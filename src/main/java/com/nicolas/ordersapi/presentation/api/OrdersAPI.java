package com.nicolas.ordersapi.presentation.api;

import java.util.List;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.datasources.*;
import com.nicolas.ordersapi.data.datasources.api.StockAPIDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.*;
import com.nicolas.ordersapi.data.models.OrderHistoryModel;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.data.models.UserModel;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;
import com.nicolas.ordersapi.data.repositories.*;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
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
@CrossOrigin(origins = "http://localhost:8080")
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
	private SwitchOrderStatusUsecase switchOrderStatusUsecase;
	private GetUserOrdersHistoryUsecase getUserOrdersHistoryUsecase;
	private SwitchOrderHistoryStatusUsecase switchOrderHistoryStatusUsecase;

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
		switchOrderStatusUsecase = new SwitchOrderStatusUsecase(orderRepository, stockRepository);
		getUserOrdersHistoryUsecase = new GetUserOrdersHistoryUsecase(orderRepository);
		switchOrderHistoryStatusUsecase = new SwitchOrderHistoryStatusUsecase(orderRepository);
	}

	private static final String WRONG_PARAMETER_MESSAGE = "Parameter in wrong format";

	// Models
	private static final OrderHistoryModel ORDER_HISTORY_MODEL = new OrderHistoryModel();
	private static final OrderModel ORDER_MODEL = new OrderModel();
	private static final UserModel USER_MODEL = new UserModel();
	private static final UserStockBalanceModel USER_STOCK_BALANCE_MODEL = new UserStockBalanceModel();

	private static final String GET_OR_CREATE_USER = "/user/{email}";
	private static final String GET_USER_STOCK_BALANCE = "/user/{id}/balance";
	private static final String GET_USER_ORDERS = "/user/{id}/orders";
	private static final String GET_USER_ORDERS_HISTORY = "/user/{id}/orders/history/{status}";
	private static final String CREATE_ORDER = "/orders/new";
	private static final String SWITCH_USER_ORDER = "/orders/{id}/switch";
	private static final String SWITCH_USER_ORDER_HISTORY = "/orders/history/{id}/switch";

	@GetMapping("/")
	public String root() { return "OrdersAPI"; }

	@GetMapping("/error")
	public String error() { return "Endpoint doesn't exist"; }
	
	@GetMapping(GET_OR_CREATE_USER)
	public ResponseEntity<Object> getOrCreateUser(@PathVariable String email) {
		if (email.isBlank()) 
			return returnBadRequest(Either.left("No email object found"));

		// Create User entity
		var user = new UserEntity();
		user.setUsername(email);

		// Open connection
		PostgreDatasource.openConnection();
		
		var result = getOrCreUsecase.call(user);
		
		return returnEntity(result, USER_MODEL);
	}

	@GetMapping(GET_USER_STOCK_BALANCE)
	public ResponseEntity<Object> getUserStockBalance(@PathVariable String id) {
		Long idUser = null;
		try {
			idUser = Long.parseLong(id);
		} catch (Exception e) {
			returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var user = new UserEntity(idUser);
		var result = getUserStockBalanceUsecase.call(user);

		return returnList(result, USER_STOCK_BALANCE_MODEL);
	}

	@GetMapping(GET_USER_ORDERS)
	public ResponseEntity<Object> getUserOrders(@PathVariable String id) {
		Long idUser;
		try {
			idUser = Long.parseLong(id);
		} catch (Exception e) {
			return returnBadRequest(Either.left("User id with wrong type"));
		}

		var user = new UserEntity(idUser);
		var result = getUserOrdersUsecase.call(user);

		return returnList(result, ORDER_MODEL);
	}

	@GetMapping(GET_USER_ORDERS_HISTORY)
	public ResponseEntity<Object> getUserOrdersHistory(@PathVariable String id, @PathVariable String status) {
		Long idUser;
		Integer statusValue;
		try {
			idUser = Long.parseLong(id);

			statusValue = Integer.parseInt(status);
			// -1 returns all values
			if (statusValue == -1)
				statusValue = null;

		} catch (Exception e) {
			return returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var orderHistory = new OrderHistoryEntity();
		orderHistory.setOrderUser(idUser);
		orderHistory.setStatus(statusValue);

		var result = getUserOrdersHistoryUsecase.call(orderHistory);

		return returnList(result, ORDER_HISTORY_MODEL);
	}

	@PostMapping(CREATE_ORDER)
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> createOrder(@RequestBody Map<String, Map<String, Object>> req) {
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

		return returnObject(result);
		
	}

	@GetMapping(SWITCH_USER_ORDER)
	public ResponseEntity<Object> switchUserOrder(@PathVariable String id) {
		Long orderId = null;

		try {
			orderId = Long.parseLong(id);
		}
		catch (Exception e) {
			returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var order = new OrderEntity(orderId);
		var result = switchOrderStatusUsecase.call(order);

		return returnEntity(result, ORDER_MODEL);
	}

	@GetMapping(SWITCH_USER_ORDER_HISTORY)
	public ResponseEntity<Object> switchUserOrderHistory(@PathVariable String id) {
		Long orderId = null;

		try {
			orderId = Long.parseLong(id);
		}
		catch (Exception e) {
			returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var orderHistory = new OrderHistoryEntity(orderId);
		var result = switchOrderHistoryStatusUsecase.call(orderHistory);

		return returnEntity(result, ORDER_HISTORY_MODEL);
	}

	private ResponseEntity<Object> returnObject(Either<Exception, ?> result) {
		PostgreDatasource.closeConnection();

		if (result.isLeft())
			return returnServerError(result);

		return ResponseEntity.ok(result.get());
	}

	private <A> ResponseEntity<Object> returnEntity(Either<Exception, A> result, IModel<A> model) {
		PostgreDatasource.closeConnection();
		
		if (result.isLeft())
			return returnServerError(result);
		
		return ResponseEntity.ok(model.toMap(result.get()));
	}
	
	private <A> ResponseEntity<Object> returnList(Either<Exception, List<A>> result, IModel<A> model) {
		PostgreDatasource.closeConnection();

		if (result.isLeft())
			return returnServerError(result);

		var body = result.get().stream().map(model::toMap);
		return ResponseEntity.ok(body);
	}
	
	private ResponseEntity<Object> returnServerError(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getLeft());
	}

	private ResponseEntity<Object> returnBadRequest(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());
	}
}