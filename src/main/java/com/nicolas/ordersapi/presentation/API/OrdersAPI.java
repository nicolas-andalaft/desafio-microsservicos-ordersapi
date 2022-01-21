package com.nicolas.ordersapi.presentation.API;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.mappers.UserMapper;
import com.nicolas.ordersapi.data.mappers.UserStockBalanceMapper;
import com.nicolas.ordersapi.data.repositories.UserRepositoryImplementation;
import com.nicolas.ordersapi.data.repositories.UserStockBalanceRepositoryImplementation;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.UserRepository;
import com.nicolas.ordersapi.domain.repositories.UserStockBalanceRepository;
import com.nicolas.ordersapi.domain.usecases.CreateUserStockBalanceUsecase;
import com.nicolas.ordersapi.domain.usecases.GetOrCreateUserUsecase;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class OrdersAPI {
	private IUserDatasource userDatasource;
	private IUserStockBalanceDatasource userStockBalanceDatasource;

	private UserRepository userRepository;
	private UserStockBalanceRepository userStockBalanceRepository;

	private GetOrCreateUserUsecase getOrCreUsecase;
	private CreateUserStockBalanceUsecase createUserStockBalanceUsecase;

	public OrdersAPI() {
		userDatasource = new PostgreUserDatasource();
		userStockBalanceDatasource = new PostgreUserStockBalanceDatasource();

		userRepository = new UserRepositoryImplementation(userDatasource);
		userStockBalanceRepository = new UserStockBalanceRepositoryImplementation(userStockBalanceDatasource);

		getOrCreUsecase = new GetOrCreateUserUsecase(userRepository);
		createUserStockBalanceUsecase = new CreateUserStockBalanceUsecase(userStockBalanceRepository);
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
			resp.put("user", UserMapper.toMap(result.get()));
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
		
		var order = UserStockBalanceMapper.fromMap((Map<String, Object>)orderMap);
		System.out.println(order.idStock);
		var result = createUserStockBalanceUsecase.call(order);
		
		// Return null on Exception
		if (result.isLeft()) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", result.getLeft());
			System.out.println(result.getLeft());
		}
		else {
			resp.put("status", "OK");
			resp.put("user", result.get());
			System.out.println(result.get());
		}
		
		return resp;
	}

	@GetMapping("/")
	public void root() {}

	@GetMapping("/error")
	public void error() {}
}