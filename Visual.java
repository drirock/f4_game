
/**
   I/O visivi
   @version 2004
   @author G. Di Stefano
*/

import javax.swing.*;

public class Visual
{  
   /**
    */
   public static void message(String prompt)
   {  
     JOptionPane.showMessageDialog(null,prompt);
   }

   /**
    */
   public static String readLine()
   {  
      return JOptionPane.showInputDialog(null,"");
   }

   /**
    */
   public static String readLine(String prompt)
   {  
      return JOptionPane.showInputDialog(null,prompt);
   }

   /**
    */
   public static char readChar(String prompt)
   { String s= new String(); 
     do
     {    
       s= JOptionPane.showInputDialog(null,prompt);
       if (s.length()!=1) 
          JOptionPane.showMessageDialog(null,"Only 1 char, please !");
     }while(s.length()!=1); 
     return s.charAt(0); 
   }


   /**
    */
   public static int readInt(String prompt)
   { while(true)
     {  
      try
      { return Integer.valueOf (JOptionPane.showInputDialog(null,prompt).trim()).intValue();
      } 
      catch(NumberFormatException e)
      { 
        JOptionPane.showMessageDialog(null,"Non e' un intero! ");       
      }
     }
   }

   /**
    */
   public static double readDouble(String prompt)
   { while(true)
     { 
      try
      {  return Double.parseDouble(JOptionPane.showInputDialog(null,prompt).trim());
      } 
      catch(NumberFormatException e)
      {  
         JOptionPane.showMessageDialog(null,"Non e' un numero double ! ");
      }
     }
   }
   
   /**

    */   
   public static float readFloat(String prompt)
   { while(true)
     {  
      try
      {  return Float.parseFloat(JOptionPane.showInputDialog(null,prompt).trim());
      } 
      catch(NumberFormatException e)
      { 
         JOptionPane.showMessageDialog(null,"Non e' un numero float ! ");
      }
     }
   }   
}
