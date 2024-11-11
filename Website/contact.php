<?php

    $connect = mysqli_connect('localhost','root','','the_haritage_cafe');
    
    if(isset($_POST['submit'])){
    	$name=$_POST['name'];
    	$email=$_POST['email'];
    	$subject=$_POST['subject'];
    	$message=$_POST['message'];


    	$to='lkrashmika@gmail.com';
    	$subject='Form Submission';
    	$message="Name : ".$name."\n"."Subject : ".$subject."\n"."Wrote The Following : "."\n\n".$message;
    	$headers="From : ".$email;


    	if (mail($to, $subject, $message,$headers)) {
    		echo "<h1>Send Succesfully , Thank You !"." ".$name.", We Will Contact You Imideatly !<h1>";
    	} else {
    		echo "Something Went Wrong!";
    		
    	}
    	
    }



















?>