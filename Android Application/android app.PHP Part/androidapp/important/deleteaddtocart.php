<?php 

	require_once '../includes/Dbwrite.php';
	$response = array();

	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());

	if ($_SERVER['REQUEST_METHOD'] == 'POST') 
	 {
	 	if (isset($_POST['deletename']) AND isset($_POST['IMEI_Number'])) 
		 {
		    $db = new Dbwrite();
			$result = $db->deleteaddtocart($_POST['deletename'],$_POST['IMEI_Number']);

			$response['error'] = false;
			$response['message'] = "Successfully Removed";
	     }
	     else
	     {
	     	$response['error'] = true;
			$response['message'] = "Something Wrong";
	     }
        	
	 }
    else 
	 {
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	 }

echo json_encode($response);
 ?>