package gameClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Time;
import java.time.LocalDateTime;



import utils.Point3D;

public class Logger_KML {
   String kml="";
   String Start=new String();
   public Logger_KML( int stage)
   {
	   
	  Start= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
           "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
           "  <Document>\r\n" +
           "    <name>" + "Game stage :"+ stage + "</name>" +"\r\n"+
           " <Style id=\"paddle-a\">\r\n" +
           "      <IconStyle>\r\n" +
           "        <Icon>\r\n" +
           "          <href>http://maps.google.com/mapfiles/kml/shapes/cycling.png</href>\r\n" +
           "        </Icon>\r\n" +
           "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
           "      </IconStyle>\r\n" +
           "    </Style>\n" ;
   }
   
   public void placemark(Point3D location,int id )
   {
	   LocalDateTime date = LocalDateTime.now();  

	   kml+=  "    <Placemark>\r\n" +
               "      <TimeStamp>\r\n" +
               "        <when>" + date+ "</when>\r\n" +
               "      </TimeStamp>\r\n" +
               "      <styleUrl>#" + id + "</styleUrl>\r\n" +
               "      <Point>\r\n" +
               "        <coordinates>" + location + "</coordinates>\r\n" +
               "      </Point>\r\n" +
               "    </Placemark>\r\n";
	   
   }
   
   
   
   public void endKml(int scenario)
   {
	   
	   kml+="  </Document>\r\n" + 
	   		"</kml>";
	   Start+=kml;
	   BufferedWriter writer = null;
	   try
	   {
	       writer = new BufferedWriter( new FileWriter("data/"+scenario+".txt"));
	       writer.write( Start);
	       writer.close();

	   }catch (Exception e) {System.out.println(e.getMessage());
		   
	   }
	   
   }

public String getString() {
	// TODO Auto-generated method stub
	return kml;
}

   
}
