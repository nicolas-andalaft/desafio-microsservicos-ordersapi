package com.nicolas.ordersapi.presentation.API;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.datasources.postgre.PostgreUserDatasource;
import com.nicolas.ordersapi.data.repositories.UserRepositoryImplementation;
import com.nicolas.ordersapi.domain.repositories.UserRepository;
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
	private UserRepository userRepository;

	private GetOrCreateUserUsecase getOrCreUsecase;

	public OrdersAPI() {
		userDatasource = new PostgreUserDatasource();
		this.userRepository = new UserRepositoryImplementation(userDatasource);

		getOrCreUsecase = new GetOrCreateUserUsecase(userRepository);
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

		var result = getOrCreUsecase.call(email.toString());
		
		// Return null on Exception
		if (result.isLeft()) {
			resp.put("status", "ERROR");
			resp.put("errorMessage", result.getLeft());
		}
		else {
			resp.put("status", "OK");
			resp.put("user", result.get());
		}

		return resp;
	}

	@GetMapping("/")
	public void root() {}

	@GetMapping("/error")
	public void error() {}
}