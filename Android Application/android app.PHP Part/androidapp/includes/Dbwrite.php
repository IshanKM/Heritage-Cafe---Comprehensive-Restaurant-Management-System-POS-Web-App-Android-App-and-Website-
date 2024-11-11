<?php 
	
	// database details

	function dbname()
		 {
		 	$DB_NAME = 'the_haritage_cafe';
		 	return $DB_NAME;
		 }

	function dbuser()
		 {
		 	$DB_USER =  'root';
		 	return $DB_USER;
		 }
	function dbpassword()
		 {
		 	$DB_PASSWORD = '';
		 	return $DB_PASSWORD;
		 }
	function dbhost()
		 {
		 	$DB_HOST = 'localhost';
		 	return $DB_HOST;
		 }
     
     //check already added user
	 function checkuser($Mobile_Number,$email,$password)
         { 
         	$pass = md5($password);
        	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
        	$qury = "SELECT ID FROM `onlineuser_register` WHERE Mobile_Number = '$Mobile_Number' OR email = '$email' OR password = '$pass'";

        	$result = mysqli_query($connect,$qury);

		  	if ( mysqli_num_rows($result) >0) 
		  		{
		  			return true;
		  		}
		  	else  
		  	    {
		  			return false;
		  	    }
         }	 

       //chek already added cart
      function checkcart($Name)
         { 

        	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
        	$qury = "SELECT * FROM `addtocart` WHERE Name = '$Name'";

        	$result = mysqli_query($connect,$qury);

		  	if ( mysqli_num_rows($result) >0) 
		  		{
		  			return true;
		  		}
		  	else  
		  	    {
		  			return false;
		  	    }
         }	    

	class Dbwrite 
	 {

		function __construct()
		 {
		 		
		 }
		
		// insert user to table
		public function reguser($email,$password,$name,$Mobile_Number,$address)
	     {
	     	if (checkuser($Mobile_Number,$email,$password)) 
	     	 {
	     		return 1;
	     	 }
	     	 else
	     	 {
	     	 	
		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "INSERT INTO `onlineuser_register` (`ID`, `Name`, `Mobile_Number`, `email`, `password`, `address`) VALUES (NULL, '$name', '$Mobile_Number', '$email', '$password', '$address');";

				$result = mysqli_query($connect,$qury);

			  	if ($result) 
			  		{
			  			return 0;

			  		}
			  	else
			  		{
			  			return 2;
			  		}
		  	 }	 	
          }

          //login 
          public function userlogin($email,$password)
           {
          		
          		$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
          		$qury = "SELECT id FROM `onlineuser_register` WHERE email = '$email' AND password = '$password'";

        		$result = mysqli_query($connect,$qury);

        		if (mysqli_num_rows($result) >0) 
        		 {
        			return true;
        		 } 
        		else 
        		 {
        			return false;
        		 }
        		
            }

            //get details for show when login
           public function getdetails($email,$password)
            {
           		
           		$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
          		$qury = "SELECT * FROM `onlineuser_register` WHERE email = '$email' OR password = '$password'";
          		$result = mysqli_query($connect,$qury);

          		$row = mysqli_fetch_array($result);

          		return $row;
            } 


           //insert to addtocart table
           public function addtocart($IMEI_Number,$Name,$Price,$Qty,$image)
	        {
	        	if (checkcart($Name)) 
	     	 	 {
	     		    return 1;
	     	     }
	     	    else
	     	     {
			     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
					$qury = "INSERT INTO `addtocart` (`ID`, `IMEI_Number`, `Name`,`Price`, `Qty`,`image`) VALUES (NULL, '$IMEI_Number', '$Name', '$Price', '$Qty', '$image')";

					$result = mysqli_query($connect,$qury);
					return 2;
			      }

		  	 }	

		  	//get from addtocart table
		  	public function getaddtocart($IMEI_Number)
	         {

		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "SELECT * FROM addtocart WHERE IMEI_Number = '$IMEI_Number'";

				$result = mysqli_query($connect,$qury);

          		return $result;

		  	 }	

		  	 //get count 
		  	public function getaddtocartcount($IMEI_Number)
	         {

		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "SELECT COUNT(ID) FROM addtocart WHERE IMEI_Number = '$IMEI_Number'";

				$result = mysqli_query($connect,$qury);
			    return $result;

		  	 }

		  	 //delete addtocart
		  	 public function deleteaddtocart($getname,$IMEI_Number)
	         {

		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "DELETE FROM addtocart WHERE Name = '$getname' AND IMEI_Number = '$IMEI_Number'";

				$result = mysqli_query($connect,$qury);
			    return $result;

		  	 }	

		  	 public function deleteallitemcart($IMEI_Number)
	         {

		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "DELETE FROM addtocart WHERE IMEI_Number = '$IMEI_Number'";

				$result = mysqli_query($connect,$qury);
			    return $result;

		  	 }	

		  	 //get ids from name in item table
		  	public function getorderid($Name)
             {
           		
           		$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
          		$qury = "SELECT id FROM `items` WHERE Name = '$Name'";
          		$result = mysqli_query($connect,$qury);

          		$row = mysqli_fetch_array($result);

          		return $row;
             }  

             //add android app orders to table
            public function storeonlineorders($cid,$cname,$caddress,$cmobilenumber,$cemail,$total,$orderids,$orderqtys,$Delivery_Charge)
	     	  {
	     	  	date_default_timezone_set('Asia/Colombo');
	     	  	$date = date('y-m-d');
	     	  	$time =  Date('h:i:s a');
		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "INSERT INTO `online_order` (`ID`,`Date`,`Time`,`Customer_ID`, `Customer_Name`, `Customer_Address`, `Cmoblile_Number`,`Customer_email`,`Total`, `Order_IDs`, `Order_qtys`, `Delivery_Charge`) VALUES (NULL,'$date','$time','$cid', '$cname', '$caddress','cmobilenumber','$cemail','$total', '$orderids', '$orderqtys','$Delivery_Charge');";

				$result = mysqli_query($connect,$qury);

			  	if ($result) 
			  		{
			  			return true;

			  		}
			  	else
			  		{
			  			return false;
			  		}
		  	 }

		  	 //delete account
		  	 public function deleteaccount($id)
	         {

		     	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
				$qury = "DELETE FROM onlineuser_register WHERE ID = '$id'";

				$result = mysqli_query($connect,$qury);
			    return $result;

		  	 }	

		  	 public function getdeliveryprice()
             {
           		
           		$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());
          		$qury = "SELECT * FROM `delivert_price` WHERE ID = '1'";
          		$result = mysqli_query($connect,$qury);

          		$row = mysqli_fetch_array($result);

          		return $row;
             }  	 	
          }
		
         