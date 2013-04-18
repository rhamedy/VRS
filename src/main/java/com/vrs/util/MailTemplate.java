package com.vrs.util;

import java.sql.Date;

import com.vrs.model.Vehicle;

public class MailTemplate {
	public static String getBookingTemplate(String uuid, Vehicle vehicle,
			Date startDate, Date endDate, boolean insurance, double totalCost) {
		StringBuffer eMail = new StringBuffer(); 
		
		eMail.append("Vehicle Booking Details \n\n"); 
		eMail.append("Dear Customer, \n\n");
		eMail.append("You hired the following vehicle. Should you wish to make any change to the booking, contact us. \n\n");
		eMail.append("<html><body>"); 
		eMail.append("<table border='1' cellspacing='0' cellpadding='0'>"); 
		eMail.append("<tr><td>Booking ID</td><td>" + uuid +"</td></tr>"); 
		eMail.append("<tr><td>Vehile Plate No</td><td>" + vehicle.getNumberPlate() +"</td></tr>"); 
		eMail.append("<tr><td>Fuel type</td><td>" + vehicle.getFuel() +"</td></tr>");
		eMail.append("<tr><td>Seating</td><td>" + vehicle.getSeating() +"</td></tr>");
		eMail.append("<tr><td>Make</td><td>" + vehicle.getMake() +"</td></tr>");
		eMail.append("<tr><td>Model</td><td>" + vehicle.getModel() +"</td></tr>");
		eMail.append("<tr><td>One day cost</td><td>" + vehicle.getDailyCost() +"</td></tr>");
		eMail.append("<tr><td>You Choose Insurance</td><td>" + (insurance==true ? "Yes": "No") +"</td></tr>");
		eMail.append("<tr><td>Total cost (£)</td><td>" + totalCost +"</td></tr>");
		eMail.append("<tr><td>Start date</td><td>" + startDate.toString() +"</td></tr>"); 
		eMail.append("<tr><td>End date</td><td>" + endDate.toString() +"</td></tr>"); 
		eMail.append("</table>"); 
		eMail.append("<br /><br /><p> Thank you for using our services</p><br /><br /><p>Oscar VRS team</p>");
		eMail.append("</body></html>"); 
		return eMail.toString(); 
		
	}
}
