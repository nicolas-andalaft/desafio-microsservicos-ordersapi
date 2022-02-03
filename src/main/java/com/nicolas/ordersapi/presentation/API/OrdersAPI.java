package com.nicolas.ordersapi.presentation.API;

import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.datasources.IStockDatasource;
import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.datasources.API.StockAPIDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.data.repositories.OrderRepository;
import com.nicolas.ordersapi.data.repositories.StockRepository;
import com.nicolas.ordersapi.data.repositories.UserRepository;
import com.nicolas.ordersapi.data.repositories.UserStockBalanceRepository;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;
import com.nicolas.ordersapi.domain.usecases.CheckForOrderMatchUsecase;
import com.nicolas.ordersapi.domain.usecases.CreateOrderUsecase;
import com.nicolas.ordersapi.domain.usecases.GetOrCreateUserUsecase;
import com.nicolas.ordersapi.domain.usecases.GetUserOrdersUsecase;

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
	}

	@GetMapping("/")
	public String root() { return "OrdersAPI"; }

	@GetMapping("/error")
	public String error() { return "Endpoint doesn't exist"; }
	
	@PostMapping("/user")
	public ResponseEntity<?> getOrCreateUser(@RequestBody Map<String, Map<String, Object>> req) {
        var data = req.get("body");
		var email = data.get("email");

		if (email == null) 
			return returnBadRequest(Either.left("No email object found"));

		var user = new UserEntity();
		user.username = email.toString();
		var result = getOrCreUsecase.call(user);
		
		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@PostMapping("/orders/new")
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, Map<String, Object>> req) {
        var data = req.get("body");
		var orderMap = data.get("order");
		
		if (orderMap == null)
			returnBadRequest(Either.left("No order object found"));
		
		Either<Exception, OrderEntity> orderResult;
		OrderEntity order = null;
		try {
			order = OrderModel.fromMap((Map<String, Object>)orderMap);
			orderResult = createOrderUsecase.call(order);
		} catch (Exception e) {
			orderResult = Either.left(e);
		}		
		
		if (orderResult.isLeft()) 
			return returnServerError(orderResult);
		
		var result = checkForOrderMatchUsecase.call(orderResult.get());

		if (result.isLeft())
			return returnServerError(result);
		else 
			return returnOk(result);
		
	}

	@GetMapping("/orders/user/{id}")
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

	private ResponseEntity<?> returnServerError(Either<?,?> result) {
		var response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getLeft());
		return response;
	}
	
	private ResponseEntity<?> returnBadRequest(Either<?,?> result) {
		var response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());
		return response;
	}
	
	private ResponseEntity<?> returnOk(Either<?,?> result) {
		var response = ResponseEntity.status(HttpStatus.OK).body(result.get());
		return response;
	}
}