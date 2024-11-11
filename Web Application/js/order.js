
// display database java script

      function breakfast()
        {
            document.getElementById('dbshow1').style.visibility ="visible";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function pizza()
          {
            document.getElementById('dbshow1').style.visibility = "hidden";
            document.getElementById('dbshow2').style.visibility = "visible";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function pasta()
          { 
            document.getElementById('dbshow1').style.visibility = "hidden";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "visible";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function soup()
          {
            document.getElementById('dbshow1').style.visibility = "hidden";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "visible";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function sandwich()
          {
            document.getElementById('dbshow1').style.visibility ="hidden";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "visible";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function desert()
          {
            document.getElementById('dbshow1').style.visibility = "hidden";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "visible";
            document.getElementById('dbshow7').style.visibility = "hidden";
          }

      function drink()
          {
            document.getElementById('dbshow1').style.visibility = "hidden";
            document.getElementById('dbshow2').style.visibility = "hidden";
            document.getElementById('dbshow3').style.visibility = "hidden";
            document.getElementById('dbshow4').style.visibility = "hidden";
            document.getElementById('dbshow5').style.visibility = "hidden";
            document.getElementById('dbshow6').style.visibility = "hidden";
            document.getElementById('dbshow7').style.visibility = "visible";
          }

      
     //clock js

      setInterval(displayclock,500);
      function displayclock(){

      var today=new Date();
      var hrs=today.getHours();
      var min=today.getMinutes();
      var sec=today.getSeconds();
      var en='AM';

      if(hrs>12){
        en='PM';
        
      }
        
      if(hrs>12){
      hrs=hrs-12;
      }

      if(hrs==0){
      hrs=12;
      }

      if(hrs<10)
      {
          hrs='0' +hrs;
      }

      if(min<10)
      {
          min='0' +min;
      }

      if(sec<10)
      {
          sec='0' +sec;
      }

      document.getElementById('clock').innerHTML= hrs+ ':'+ min +':' + sec + ' '+ en;

      }

      

     


