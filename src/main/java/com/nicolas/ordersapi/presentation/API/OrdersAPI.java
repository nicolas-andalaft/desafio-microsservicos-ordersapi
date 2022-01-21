package com.nicolas.ordersapi.presentation.API;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreeOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.data.models.UserModel;
import com.nicolas.ordersapi.data.repositories.OrderRepository;
import com.nicolas.ordersapi.data.repositories.UserRepository;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;
import com.nicolas.ordersapi.domain.usecases.CreateOrderUsecase;
import com.nicolas.ordersapi.domain.usecases.GetOrCreateUserUsecase;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	// Repository interfaces
	private IUserRepository userRepository;
	private IOrderRepository orderRepository;
	
	// Usecases
	private GetOrCreateUserUsecase getOrCreUsecase;
	private CreateOrderUsecase createOrderUsecase;

	public OrdersAPI() {
		// Datasource implementations
		userDatasource = new PostgreUserDatasource();
		orderDatasource = new PostgreeOrderDatasource();

		// Repository implementations
		userRepository = new UserRepository(userDatasource);
		orderRepository = new OrderRepository(orderDatasource);
		
		// Usecase instances
		getOrCreUsecase = new GetOrCreateUserUsecase(userRepository);
		createOrderUsecase = new CreateOrderUsecase(orderRepository);
	}
	
	@PostMapping("/user")
	public Map<String, Object> getOrCreateUser(@RequestBody Map<String, Map<String, Object>> req) {
		var resp = new HashMap<String, Object>(){};

        var data = req.get("body");
		var email = data.get("email");

		if (email == null) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", "No email found");
			return resp;
		}

		var user = new UserEntity();
		user.username = email.toString();
		var result = getOrCreUsecase.call(user);
		
		// Return null on Exception
		if (result.isLeft()) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", result.getLeft());
		}
		else {
			resp.put("status", "OK");
			resp.put("user", UserModel.toMap((UserModel)result.get()));
		}
		
		return resp;
	}

	@PostMapping("/order/new")
	public Map<String, Object> createOrder(@RequestBody Map<String, Map<String, Object>> req) {
		var resp = new HashMap<String, Object>(){};
		
        var data = req.get("body");
		var orderMap = data.get("order");
		
		if (orderMap == null) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", "No order found");
			return resp;
		}
		
		Either<Exception, Integer> result;
		try {
			var order = OrderModel.fromMap((Map<String, Object>)orderMap);
			result = createOrderUsecase.call(order);
		} catch (Exception e) {
			result = Either.left(e);
		}		
		
		return resultChecker(result);
	}

	@GetMapping("/")
	public void root() {}

	@GetMapping("/error")
	public void error() {}

	private Map<String, Object> resultChecker(Either<Exception, ?> result) {
		var resp = new HashMap<String, Object>(){};

		if (result.isLeft()) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", result.getLeft());
			System.err.println(result.getLeft());
		}
		else {
			resp.put("status", "OK");
			resp.put("user", result.get());
		}

		return resp;
	}
}