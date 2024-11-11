<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
				$db = new Dbwrite();
				$result = $db->getdeliveryprice();
		    	$response['deliveryprice'] = $result['Delivery_Charge'];				
		} 
	else 
	{
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	}
	


echo json_encode($response);


