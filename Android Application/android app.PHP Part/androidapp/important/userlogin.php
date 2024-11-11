<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
			if (isset($_POST['email']) and isset($_POST['password'])) 
			{
				$db = new Dbwrite();
				$result = $db->userlogin($_POST['email'],$_POST['password']);

				if($result)
				{
					$row = $db->getdetails($_POST['email'],$_POST['password']);
					$response['error'] = false;
					$response['id'] = $row['ID'];
					$response['name'] = $row['Name'];
					$response['Mobile_Number'] = $row['Mobile_Number'];
					$response['email'] = $row['email'];
					$response['address'] = $row['address'];

					$response['message'] = "Login Successfully";

				}
				else
				{
					$response['error'] = true;
					$response['message'] = "E-Mail or Password Invalid ";
				}
				

			}
			else
			{
				$response['error'] = true;
				$response['message'] = "Please fill all fields";
			}
		} 
	else 
	{
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	}
	


echo json_encode($response);


