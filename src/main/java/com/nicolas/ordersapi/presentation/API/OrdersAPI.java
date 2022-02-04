package com.nicolas.ordersapi.presentation.API;

import java.util.Map;

import com.nicolas.ordersapi.data.datasources.*;
import com.nicolas.ordersapi.data.datasources.API.StockAPIDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.*;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.data.repositories.*;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.*;
import com.nicolas.ordersapi.domain.usecases.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.control.Either;

@RestController
@CrossOrigin
class OrdersAPI {

	// Datasource interfaces
	private IUserDatasource userDatasource;
	private IOrderDatasource orderDatasource;
	private IUserStockBalanceDatasource userStockBalanceDatasource;
	private IStockDatasource stockDatasource;
	
	// Repository interfaces
	private IUserRepository userRepository;
	private IOrderRepository orderRepository;
	private IUserStockBalanceRepository userStockBalanceRepository;
	private IStockRepository stockRepository;
	
	// Usecases
	private GetOrCreateUserUsecase getOrCreUsecase;
	private CreateOrderUsecase createOrderUsecase;
	private GetUserOrdersUsecase getUserOrdersUsecase;
	private CheckForOrderMatchUsecase checkForOrderMatchUsecase;
	private GetUserStockBalanceUsecase getUserStockBalanceUsecase;

	public OrdersAPI() {
		// Datasource implementations
		userDatasource = new PostgreUserDatasource();
		orderDatasource = new PostgreOrderDatasource();
		userStockBalanceDatasource = new PostgreUserStockBalanceDatasource();
		stockDatasource = new StockAPIDatasource();

		// Repository implementations
		userRepository = new UserRepository(userDatasource);
		orderRepository = new OrderRepository(orderDatasource);
		userStockBalanceRepository = new UserStockBalanceRepository(userStockBalanceDatasource);
		stockRepository = new StockRepository(stockDatasource);
		
		// Usecase instances
		getOrCreUsecase = new GetOrCreateUserUsecase(userRepository, stockRepository, userStockBalanceRepository);
		createOrderUsecase = new CreateOrderUsecase(orderRepository, userStockBalanceRepository, stockRepository);
		getUserOrdersUsecase = new GetUserOrdersUsecase(orderRepository);
		checkForOrderMatchUsecase = new CheckForOrderMatchUsecase(orderRepository, userRepository, userStockBalanceRepository);
		getUserStockBalanceUsecase = new GetUserStockBalanceUsecase(userStockBalanceRepository);
	}

	private final String getOrCreateUser = "/user/{email}";
	private final String getUserStockBalance = "/user/{id}/balance";
	private final String getUserOrders = "/user/{id}/orders";
	private final String createOrder = "/orders/new";

	@GetMapping("/")
	public String root() { return "OrdersAPI"; }

	@GetMapping("/error")
	public String error() { return "Endpoint doesn't exist"; }
	
	@GetMapping(getOrCreateUser)
	public ResponseEntity<?> getOrCreateUser(@PathVariable String email) {
		if (email.isBlank()) 
			return returnBadRequest(Either.left("No email object found"));

		// Create User entity
		var user = new UserEntity();
		user.username = email.toString();

		// Open connection
		PostgreDatasource.openConnection();
		
		var result = getOrCreUsecase.call(user);
		
		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@GetMapping(getUserStockBalance)
	public ResponseEntity<?> getUserStockBalance(@PathVariable String id) {
		Long id_user = null;
		try {
			id_user = Long.parseLong(id);
		} catch (Exception e) {
			returnBadRequest(Either.left("Parameter in wrong format"));
		}

		var user = new UserEntity();
		user.id = id_user;

		var result = getUserStockBalanceUsecase.call(user);

		if (result.isLeft())
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@GetMapping(getUserOrders)
	public ResponseEntity<?> getUserOrders(@PathVariable String id) {
		Long id_user;
		try {
			id_user = Long.parseLong(id);
		} catch (Exception e) {
			return returnBadRequest(Either.left("User id with wrong type"));
		}

		var user = new UserEntity();
		user.id = id_user;
		var result = getUserOrdersUsecase.call(user);

		if (result.isLeft())
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@PostMapping(createOrder)
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, Map<String, Object>> req) {
        var data = req.get("body");
		var orderMap = data.get("order");
		
		if (orderMap == null)
			returnBadRequest(Either.left("No order object found"));
		
		Either<Exception, OrderEntity> orderResult;
		OrderEntity order = null;

		// Open connection
		PostgreDatasource.openConnection();

		order = OrderModel.fromMap((Map<String, Object>)orderMap);
		orderResult = createOrderUsecase.call(order);
		
		if (orderResult.isLeft()) 
			return returnServerError(orderResult);
		
		var result = checkForOrderMatchUsecase.call(orderResult.get());

		if (result.isLeft())
			return returnServerError(result);
		else 
			return returnOk(result);
		
	}

	private ResponseEntity<?> returnServerError(Either<?,?> result) {
		PostgreDatasource.closeConnection();

		var response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getLeft());
		return response;
	}
	
	private ResponseEntity<?> returnBadRequest(Either<?,?> result) {
		PostgreDatasource.closeConnection();

		var response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());
		return response;
	}
	
	private ResponseEntity<?> returnOk(Either<?,?> result) {
		PostgreDatasource.closeConnection();

		var response = ResponseEntity.status(HttpStatus.OK).body(result.get());
		return response;
	}
}