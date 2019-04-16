package com.amazon.profile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazon.db.db_connection;

@Service
public class ProfileService 
{
	static int booking_id,quantity,seat_id,payment_id,venue_id,event_id;
	static double price;	
	static String event, venue;

	public List<History> viewHistory(String email) 
	{
		System.out.println("Yayy 2!");
		List<History> list = new ArrayList<History>();
		try
		{
			Connection con=db_connection.get_connection();
			Statement st=con.createStatement();
			String q="select Booking_id,quantity,total_price,seat_id,payment_id from amazon1.tickets where Email3='"+email+"';";
			ResultSet rs=st.executeQuery(q);
			while(rs.next())
			{
				booking_id=rs.getInt(1);
				quantity=rs.getInt(2);
				price=rs.getDouble(3);
				seat_id=rs.getInt(4);
				payment_id=rs.getInt(5);
				
				getId();
				System.out.println(venue_id+" "+event_id);
				
				getEvent();
				System.out.println(event);
				
				getVenue();
				System.out.println(venue);
					
				list.add(new History(booking_id,quantity,seat_id,payment_id,price,event, venue));
				System.out.println(booking_id+" "+quantity+" "+seat_id+" "+payment_id+" "+price+" "+event+" "+venue);
				
				//System.out.println(booking_id+" "+quantity+" "+seat_id+" "+payment_id+" "+price+" "+event+" "+venue);
				con.close();
			}
			
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		System.out.println(list.toString());
		return null;
	}
	
	public void getId()
	{
		Connection con=db_connection.get_connection();
		try 
		{
			Statement st1=con.createStatement();
			String q1="select venue_id,event_id from amazon1.seats where seat_id='"+seat_id+"';";
			ResultSet rs1=st1.executeQuery(q1);
			if(rs1.next())
			{
				venue_id=rs1.getInt(1);
				event_id=rs1.getInt(2);
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getVenue()
	{
		Connection con=db_connection.get_connection();
		try 
		{
			Statement st2=con.createStatement();
			String q2="select name from amazon1.venue where venue_id='"+venue_id+"';";
			ResultSet rs2=st2.executeQuery(q2);
			if(rs2.next())
				venue=rs2.getString(1);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getEvent()
	{
		Connection con=db_connection.get_connection();
		try 
		{
			Statement st3=con.createStatement();
			String q3="select event_name from amazon1.event where event_id='"+event_id+"';";
			ResultSet rs3=st3.executeQuery(q3);
			if(rs3.next())
				event=rs3.getString(1);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateProfile(String email,String name, long phone, String password, String city, String filename, char g) 
	{
		try
		{
			Connection con=db_connection.get_connection();
			Statement st=con.createStatement();
			String q="UPDATE `amazon1`.`user` SET name='"+name+"',phone_no='"+phone+"', password='"+password+"',address='"+city+"', gender='"+g+"' WHERE Email = '"+email+"';";
			st.executeUpdate(q);
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public List<String> viewProfile(String email)
	{
		List<String> details=new ArrayList<>();
		String name = null;
		String add = null;
		long ph = 0;
		String pho = null;
		String g = null;
		
		try
		{
			Connection con=db_connection.get_connection();
			Statement st=con.createStatement();
			String q="select * from amazon1.user where Email='"+email+"'";
			ResultSet rs=st.executeQuery(q);
			//System.out.println(name+"\n"+add+"\n"+ph+"\n"+pho+"\n"+g);
			if(rs.next())
			{
				name=rs.getString(2);
				add=rs.getString(3);
				ph=rs.getLong(4);
				pho=rs.getString(6);
				g=rs.getString(7);
			}
			//System.out.println(name+"\n"+add+"\n"+ph+"\n"+pho+"\n"+g);
			details.add(name);
			details.add(add);
			details.add(String.valueOf(ph));
			details.add(pho);
			details.add(g);			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return details;
	}
}
