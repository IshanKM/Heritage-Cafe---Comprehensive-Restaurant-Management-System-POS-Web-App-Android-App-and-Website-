<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
			if (isset($_POST['name']) and isset($_POST['email']) and isset($_POST['password']) and isset($_POST['address']) and isset($_POST['Mobile_Number'])) 
			{
				$db = new Dbwrite();
				$result = $db->reguser($_POST['email'],$_POST['password'],$_POST['name'],$_POST['Mobile_Number'],$_POST['address']);
				if($result == 0)
				{
					$response['error'] = false;
					$response['message'] = "User Register Successfully";
				}
				elseif( $result == 2)
				{
					$response['error'] = true;
					$response['message'] = "Some error.please try Again";
				}
				elseif ($result == 1) 
				{
					$response['error'] = true;
					$response['message'] = "This Information is Already Used.please Change Id number OR E-mail AND Password ";
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


