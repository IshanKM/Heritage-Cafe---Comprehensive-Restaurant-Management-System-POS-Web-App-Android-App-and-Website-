<!DOCTYPE html>
<html>
<head>
  <title>The Haritage CaFe Online</title>
  <style>
      *
      {
        margin: 0;
        padding: 0;
      }

      .header
      {
        height: 100vh;
      }

      .video
      {
        min-width: 100%;
        min-height: 100%;
        right: 0;
        bottom: 0;
        position: absolute;
        width: auto;  
      }

      @media (max-aspect-ratio:16/9)
      {
        .video
        {
          width: auto;
          height: 100%;
        }
        .topicshop
        {
          font-size: 30px;
        }
      }

      .btn
      {
        position: absolute;
        left: 50%;
        top: 80%;
        transform: translate(-50%,-50%);
        text-align: center;
        border: none;
        padding: 50px;
        width: 300px;
        font-family: "montserrat",sans-serif;
        font-size: 30px;
        text-transform: uppercase;
        border-radius: 6px;
        cursor: pointer;
        color: #fff;
        background-size: 200%;
        transition: 0.6s;
        background-image: linear-gradient(to left,#FFC312,#EE5A24,#FFC312);
      }
      .btn:hover
      {
        background-position: right;
      }

      .logo
      {
        position: absolute;
        left: 50%;
        top: 20%;
        transform: translate(-50%,-50%);
        width: 500px;
        height: 300px;
      }

      .topicshop
      {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
        padding: 20px;
        color: white;
        font-size: 30px;
        text-transform: uppercase;
        font-family: 'Fredoka One', cursive;
        border: 4px solid red;
        border-radius: 5px;
        background: black;
      }




  </style>
</head>
<body>


<section class="header"> 
  <video autoplay loop  class="video">
    <source src="video/shop.mp4" type="video/mp4">

  </video>
  <img src="img/logoharitage.png" class="logo">

<?php 
    

    if (isset($_POST['storedborder']) and isset($_POST['storeid']) and isset($_POST['storeqty']) and isset($_POST['total']))
      {

       $order_details =  $_POST['storedborder'];
       $item_ids = substr( $_POST['storeid'],0,-1);
       $item_qtys =  $_POST['storeqty'];
       $item_total =  $_POST['total'];
       $table_no = $_POST['tbleslectarea'];
       $date = Date('Y-m-d h:i:s a');
       $connect = mysqli_connect("localhost","root","","the_haritage_cafe");


       $qury= "INSERT INTO `kitchen`(`ID`, `TIME`, `DETAILS`, `PRICE`, `TABLE_NO`,`IDs_in_Order`,`QTYs_in_Order`) VALUES (NULL, '$date', '$order_details', '$item_total', '$table_no', '$item_ids','$item_qtys')";

       $result = mysqli_query($connect,$qury);

       if ($result) 
            { 
              echo '<font class="topicshop"> Thank You.See You Again</font>';
            }
          else
            {
              echo '<font class="topicshop"> Sorry.Something Wrong,Please Try Again..!</font>';
            }

      }
    else
      {
        echo '<font class="topicshop"> Sorry.Something Wrong,Please Try Again..!</font>';
      } 

   session_start();
   session_destroy();

 ?>
  <a href="index.html"><button class="btn">Click Here</button> </a>
</section>


</body>
</html>