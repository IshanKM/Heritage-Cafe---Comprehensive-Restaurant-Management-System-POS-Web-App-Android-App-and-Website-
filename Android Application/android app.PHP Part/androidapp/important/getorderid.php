<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
			if (isset($_POST['Name'])) 
			{
				$db = new Dbwrite();
				$result = $db->getorderid($_POST['Name']);
		    	$response['id'] = $result['id'];				

			}
			else
			{
				$response['error'] = true;
				$response['message'] = "Connection Problem";
			}
		} 
	else 
	{
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	}
	


echo json_encode($response);


