<?php 

	require_once '../includes/Dbwrite.php';
	$response = array();

	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());

	if ($_SERVER['REQUEST_METHOD'] == 'POST') 
	 {
	 	if (isset($_POST['id'])) 
		 {
		    $db = new Dbwrite();
			$result = $db->deleteaccount($_POST['id']);

			$response['error'] = false;
			$response['message'] = "Successfully Deleted";
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