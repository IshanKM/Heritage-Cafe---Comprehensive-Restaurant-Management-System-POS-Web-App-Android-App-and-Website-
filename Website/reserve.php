<?php
 
  $connect = mysqli_connect('localhost','root','','the_haritage_cafe');
  
    if(isset($_POST['submit'])){
    	$fname=$_POST['frist_name'];
    	$lname=$_POST['last_name'];
    	$state=$_POST['state'];
    	$email=$_POST['email'];
    	$subject=$_POST['subject'];
    	$phone=$_POST['phone'];
    	$guest=$_POST['guest'];
    	$date=$_POST['datepicker'];


    	$to='lkrashmika@gmail.com';
    	$subject='Form Submission';
    	$message="Name : ".$fname." ".$lname."\n" "State :" . $state. "Mobile Number: ".$phone."\n"."Number Of Guests : ".$guest."\n" "Date : " .$date."\n" .$message;
    	$headers="From : ".$email;


    	if (mail($to, $subject, $message,$headers)) {
    		echo "<h1>Send Succesfully , Thank You !"." ".$fname.", We Will Contact You Imideatly !<h1>";
    	} else {
    		echo "Something Went Wrong!";
    		
    	}





?>