package org.makerminds.jcoaching.restaurantapp;

import java.util.Scanner;

import org.makerminds.jcoaching.restaurantapp.controller.menu.MenuPrinter;
import org.makerminds.jcoaching.restaurantapp.controller.order.IOrderCalculator;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderAmount;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderCalculatorGER;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderCalculatorKS;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderManager;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderPrinter;
import org.makerminds.jcoaching.restaurantapp.model.ApplicationMode;
import org.makerminds.jcoaching.restaurantapp.model.Client;
import org.makerminds.jcoaching.restaurantapp.model.Location;
import org.makerminds.jcoaching.restaurantapp.model.Menu;
import org.makerminds.jcoaching.restaurantapp.model.Restaurant;
import org.makerminds.jcoaching.restaurantapp.model.order.Order;

public class RestaurantApp {
	
	private static Location currentLocation;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		currentLocation = getCurrentLocation();
		
		ApplicationMode applicationMode = getApplicationMode();
		validateApplication(applicationMode);
		
	}
	
	private static Location getCurrentLocation() {
		
		System.out.println("Please enter a location : ");
		int locationID = scanner.nextInt();
	
		
		switch(locationID) {
		case 1:
			return Location.GERMANY;
			
		case 2:
			return Location.KOSOVO;
			
		default: 
			throw new IllegalArgumentException("No valid application mode selected! Application does not support " + locationID);
	
		}
		}
	
	private static void validateApplication(ApplicationMode applicationMode) {
		
		switch (applicationMode) {
		case ORDER:
			// run order method
			runOrderProcess();
			break;
		case TABLE_RESERVATION:
			runTableReservationProcess();
			break;
		default:
			throw new IllegalArgumentException("No valid application mode selected! Application does not support " + applicationMode);
		
		}
		
	}
	

	private static ApplicationMode getApplicationMode() {
		
		System.out.println("Please enter an application mode: ");
		int applicationModeID = scanner.nextInt();
	
		
		if(applicationModeID == 1) {
			return ApplicationMode.ORDER;
		}
		else if(applicationModeID == 2) {
			return ApplicationMode.TABLE_RESERVATION;
		}
		else {
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("The selected application mode id: ");
			stringBuilder.append(applicationModeID);
			stringBuilder.append(" is not supported. Valid application modes: [1,2]");
			
			throw new IllegalArgumentException(stringBuilder.toString());
		}
		
	}
	
	private static void runOrderProcess() {
		Restaurant restaurant = new Restaurant("Route 66", "Rruga e Hamburgerave");

		Client client = new Client("Filan Fisteku", "+383123456", "Rruga e Xhamise", "11000");
		
		// show the menu
		Menu menu = new Menu();
		MenuPrinter menuPrinter = new MenuPrinter();
		menuPrinter.printMenu(menu);

		OrderManager orderManager = new OrderManager();

		Order order1 = orderManager.createOrder(menu);
		Order order2 = new Order();
		Order order3 = new Order();
		Order order4 = new Order();
		Order order5 = new Order();

		orderManager.addOrder(order1);
		orderManager.addOrder(order2);
		orderManager.addOrder(order3);
		orderManager.addOrder(order4);
		orderManager.addOrder(order5);

		// dynamic load of the appropriate based on the location
		IOrderCalculator orderCalculator = getOrderCalculator();

		OrderAmount orderAmount = orderCalculator.calculateOrderAmount(order1);

		OrderPrinter orderPrinter = new OrderPrinter();
		orderPrinter.printOrderInfo(order1, restaurant, client, orderAmount, orderCalculator.getVATRate(false));
	}
	
	private static IOrderCalculator getOrderCalculator() {
		switch(currentLocation) {
		case KOSOVO: 
			return new OrderCalculatorKS();
		case GERMANY: 
			return new OrderCalculatorGER();
		}
		System.err.println("Current location is invalid.");
		return null;
	}

	private static void runTableReservationProcess() {
		System.out.println("The selected table nr. 4 was reserved successfully!");
	}
}
