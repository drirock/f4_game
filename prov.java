import java.util.*;

//Librerie per creare l'interfaccia
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class prov extends JFrame
{
	//Interfaccia	
	private JButton nuova=new JButton("Nuova partita");
  	private JButton salva=new JButton("Salva");  
  	private JButton carica=new JButton("Carica");  
  	private JButton aiuto=new JButton("Help");  
  	private JButton score=new JButton("Punteggi");
  	private JButton arreso=new JButton("Mi arrendo !");
  	private JButton esci=new JButton("Esci");

	private JButton annulla=new JButton("Annulla ultima \nmossa");

    ImageIcon sfondo=new ImageIcon("Forest.jpg");
    JLabel background=new JLabel(sfondo);
    
    ImageIcon emptygrid=new ImageIcon("griglia1.jpg");  	
    ImageIcon p1=new ImageIcon("player1.jpg");  	
    ImageIcon p2=new ImageIcon("player2.jpg");
    
    final int r=6;
   	final int c=7;
  
   	JLabel griglia[][]=new JLabel[r][c];
   	
   //	int ri=9;
   //	int ci=9;
   	
   	int mappa[][]=new int[r][c];
   		
		
	final int dim_griglia=50;
		
	int mr=60;
   	int mc=240;
   	
   	int x=0;
   	int y=0;
   	
   	int px=0;
   	int py=0;
   	
   	int perso=0;
   	int lastmove=-1;
    
    int turno=0;
    
    int giocate=0;
   
    
    public	Container mio_contenitore= this.getContentPane();
    
    //Giocatori
    String giocatore1="Giocatore1";
    String giocatore2="Pc";
 	
    JTextField gioca_turno=new JTextField("\t\t\t\tFORZA 4 RULEZ !!");
    
    //proprieta' file    
    final String nomefile="save";
	final String punti="score";
    int gioca=0;
    int mosse1=0;
    int mosse2=0;
        
  	public prov()
  	{
  		final int altezza=30;
  		
   		final int largh_button=120;
   		final int colonna=20;
   		final int riga=60;
   		 		
  		
  		mio_contenitore.setLayout(new BorderLayout(5,5));
  		mio_contenitore.setBackground(Color.red);
  		
  		//Bottoni scelta
  		
  		mio_contenitore.add(nuova);
  		mio_contenitore.add(salva);
  		mio_contenitore.add(carica);
  		mio_contenitore.add(aiuto);
  		mio_contenitore.add(score);
  		mio_contenitore.add(arreso);
  		mio_contenitore.add(esci);
  		mio_contenitore.add(annulla);
  		mio_contenitore.add(gioca_turno,BorderLayout.SOUTH);
  		
  		
  		nuova.setBounds (colonna,riga,largh_button,altezza);
   		salva.setBounds (colonna,120,largh_button,altezza);
   		carica.setBounds (colonna,180,largh_button,altezza);
   		aiuto.setBounds (colonna,240,largh_button,altezza);
   		score.setBounds (colonna,300,largh_button,altezza);
   		arreso.setBounds (colonna,360,largh_button,altezza);
   		esci.setBounds (colonna,420,largh_button,altezza);
   		annulla.setBounds(610,180,largh_button+60,altezza+10);
   		
  
  		for(int i=0;i<griglia.length;i++)
  		{
  			for(int j=0;j<griglia[i].length;j++)
  			{
  				griglia[i][j]=new JLabel(emptygrid,JLabel.CENTER);
  				griglia[i][j].setBounds(mc+i*dim_griglia,mr+j*dim_griglia,dim_griglia,dim_griglia);
  				mappa[i][j]=3;
  		
  				mio_contenitore.add(griglia[i][j],BorderLayout.CENTER);
  				
  					
  			}
  		}
  		
  		//Carica punteggi
  		
  			try
   				{
   					 FileInputStream f = new FileInputStream("score");
     				 ObjectInputStream fIN = new ObjectInputStream(f);
     				 
     				 giocatore1=(String)fIN.readObject();
     				 giocatore2=(String)fIN.readObject();
     				 mosse1=fIN.readInt();
     				 mosse2=fIN.readInt();
     				 giocate=fIN.readInt();
     			}
     			
     		catch (Exception e)
   				{
     				System.out.println("Nessun punteggio disponibile, errore: "+e);
   				}
   		
   		
   		JFrame.setDefaultLookAndFeelDecorated(true);
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	
  	
  		nuova.addMouseListener(new gestoreNuova());
  		salva.addMouseListener(new gestoreSalva());  	
  		carica.addMouseListener(new gestoreCarica());  	
  		aiuto.addMouseListener(new gestoreAiuto()); 
  		score.addMouseListener(new gestoreScore());
  		arreso.addMouseListener(new gestoreArreso()); 
  		esci.addMouseListener(new gestoreEsci());
  		annulla.addMouseListener(new gestoreAnnulla());
  		
		gioca_turno.setEditable(false);
		
  		mio_contenitore.add(background);
  		background.setBounds(0,0,800,600);
  		background.setVisible(true);

		this.addMouseListener(new Clicca());
		
  		this.setTitle("*** Forza 4 ***");
  		this.setBounds(100,100,800,600); //pos_x,pos_y, dim_x, dim_y  	dimensione finestra
		this.setResizable(false);
  		this.setVisible(true);
  		
 	}
 	
 	class gestoreNuova extends MouseAdapter 
 	{
 		
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{   			
   			azzera();
			giocatori();
			gioca=1;
			giocate++;
   			
   			Random caso=new Random();
   			
   			for(int i=0;i<10;i++) {turno=caso.nextInt(2);}
   			
   		 	if(turno==0)gioca_turno.setText("E' il turno di "+giocatore1);
   			else { if(turno==1)gioca_turno.setText("E' il turno di "+giocatore2); }

			System.out.print("turno: "+turno+"\n\n" );
			
         }
			
	}//fine gestore di nuova partita
	
	class gestoreAnnulla extends MouseAdapter 
 	{
 		
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{      		
   			if(gioca==1)
   			{
   				if(px==0 && py==0)
   				Visual.message("Fai almeno una mossa    --__--");
   				
   				System.out.println("\nx= "+px+"\ny= "+py);
   				griglia[px][py].setIcon(emptygrid);		
   				mappa[px][py]=3;
   				
   				if(turno==1)	turno=0;
   				else			turno=1;
   			}
   			else
   			{ if(px==0 && py==0)   	Visual.message("Ancora non hai iniziato a giocare e gia' hai dei ripensamenti?!?"); }
   		}
   	}
				
	class Clicca extends MouseAdapter 
 	{
   		public void mouseClicked(MouseEvent oggEvento)
   		{ 	
   			int	j=0; 
 			int	i=0;
 				
 			if(gioca==1)
			{	
				i=riga(oggEvento.getX());
 				j=colonna(oggEvento.getY());
      			
 				
 				
 				
 				if(turno==0)
 				{	
 			//		j=colonna(oggEvento.getY());
      		//		i=riga(oggEvento.getX());
      				
  			 		if (i>=0 && i<=5 && j>=0 && j<=6 && mappa[i][j]==3);
      				{     
      					for(int a=6;a>=0;a--)
      					{
      						if(mappa[i][a]!=0 && mappa[i][a]!=1 && turno==0) 	
 							{
 								griglia[i][a].setIcon(p1);
 								mappa[i][a]=0;
 								
 								px=i;	
 								py=a;
 								
 								vittoria();
 								
 								if(perso==1)
 								{
 								System.out.print("vinto "+giocatore1);
 								Visual.message("Complimenti "+giocatore1+" hai vinto !");
 								lastmove=0;
 								giocate++;
 								azzera();
 								}
 								turno=1;
 								gioca_turno.setText("E' il turno di "+giocatore2);
 								mosse1++;			
 							}
 							System.out.println("\na= "+a);
 						}
 							System.out.println("\nrigha: "+i+" colonna: "+j);	
 							
 					}
 					
 				}
 				
 				else
 				{	
   					if(turno==1 && !giocatore2.equals("Pc")) 
   					{
   			//			j=colonna(oggEvento.getY());
 			//			i=riga(oggEvento.getX());
 						
   						if (i>=0 && i<=5 && j>=0 && j<=6 && mappa[i][j]!=0 || mappa[i][j]!=1);
      					{   
      						for(int a=6;a>=0;a--)
      						{
      							if(mappa[i][a]!=0 && mappa[i][a]!=1 && turno==1) 	
 								{
 									griglia[i][a].setIcon(p2);
 									mappa[i][a]=1;
 									
 									px=i;	
 									py=a;
 									
 									vittoria();
 							
 									if(perso==1)
 									{
 										System.out.print("vinto "+giocatore2);
 										Visual.message("Complimenti "+giocatore2+" hai vinto !");
 										lastmove=1;
 										giocate++;
 										azzera();
 									}
 									turno=0;
 									gioca_turno.setText("E' il turno di "+giocatore1);
 									mosse2++;
 								}
 							}
 							
 							System.out.println("\nrigha: "+i+" colonna: "+j); 							 							
 						}
   					}
/*   					else
   					{
   						if((turno==1 && giocatore2.equals("Pc")))
   							giocatore2();
   					}
*/ 				}
   			}// fine if
   			
 		}
 			
 	}//Fine gestore clicca
 			
 
 
 	class gestoreSalva extends MouseAdapter 
 	{
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{
     			try
     			{

     				FileOutputStream f = new FileOutputStream(nomefile);
      				ObjectOutputStream fOUT = new ObjectOutputStream(f);
    
      				for(int i=0;i<griglia.length;i++)
  					{
  						for(int j=0;j<griglia[i].length;j++)
  						{
       						fOUT.writeInt(mappa[i][j]);
       					}
      				}
    				
    				fOUT.writeInt(giocate);
    				fOUT.writeInt(turno);
    				fOUT.writeInt(mosse1);
    				fOUT.writeInt(mosse2);
    				fOUT.writeObject(giocatore1);
    				fOUT.writeObject(giocatore2);
    				
      				fOUT.flush();
      				f.close();
      				Visual.message("Partita salvata");
      				gioca=1;
   				}
   				
   				catch(Exception eccez)
     			{ 
     				Visual.message("Non e' possibile salvare la partita per il seguente errore: " + eccez.getMessage());  
     			}
 		}
 	}  // fine gestore salvataggio
  
  
 	class gestoreCarica extends MouseAdapter 
 	{
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{
				try
   					{
   						 FileInputStream f = new FileInputStream(nomefile);
     				 	 ObjectInputStream fIN = new ObjectInputStream(f);
    			
    					for(int i=0;i<griglia.length;i++)
  						{
  							for(int j=0;j<griglia[i].length;j++)
  							{	
      				 			try
        						{ 

        							mappa[i][j]=fIN.readInt();
        							if(mappa[i][j]==0)
        							griglia[i][j].setIcon(p1);
        							
        							if(mappa[i][j]==1)
        							griglia[i][j].setIcon(p2);
        							
        							if(mappa[i][j]!=1 && mappa[i][j]!=0)
        							griglia[i][j].setIcon(emptygrid);
          
           						}		
        		
      							catch(EOFException e) 
        						{ 
        							Visual.message("Non e' stato possibile caricare la partita errore "+e);
    							}
    						}
           				}
           				
           				giocate=fIN.readInt();
           				turno=fIN.readInt();
           				mosse1=fIN.readInt();
           				mosse2=fIN.readInt();
           				giocatore1=(String)fIN.readObject();
           				giocatore2=(String)fIN.readObject();
           				
           			Visual.message("Partita caricata ^_^");
           			gioca=1;
   					}
  				catch (Exception e)
   				{
     				Visual.message("NON e' possibile caricare il la partita, non ci sono salvataggi!");
   				}
    		
   		}
   		
 	}   // fine gestore carica partita
  
  
 	class gestoreAiuto extends MouseAdapter 
 	{
 	
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{     	
			Visual.message("\t*** Forza 4 ***\n\nLo scopo di questo gioco e' riuscire a creare una fila di 4 gettoni del tuo colore\nin orizzontale, in verticale, in diagonale\n\nE nel caso non ci sia nessuno a giocare con voi\nnessun problema!!\nNon cambiate il nome del 2� giocatore e giocherete contro il vostro pc!!!\n\n\t*** Buon Divertimento!! *** ");
   		}
 	}   // fine gestore di aiuto
  
  
 	class gestoreScore extends MouseAdapter 
 	{
 	
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{
  
   			JFrame punti=new JFrame("*** SCORE ***");
   			JLabel sfond=new JLabel(sfondo);
   			
   			mio_contenitore.add(punti);	
   			
   			JLabel p1=new JLabel("Giocatore");
   			JLabel p2=new JLabel("Mosse");
   			
   			int cont=mosse1;
   			int cont2=mosse2;
   			
   			JLabel p1e1[]=new JLabel[giocate];
   			JLabel p2e1[]=new JLabel[giocate];
   			
   			for(int i=0;i< giocate;i++)
     		{
     		mio_contenitore.add(p1e1[i]);
     		mio_contenitore.add(p2e1[i]);
     		//punti.getContentPane().add(p2e1[i]);
     		
     		}
   			
   			if(cont<cont2 && perso==1 && lastmove==0 || "Pc".equals(giocatore2)!=true)
   			{
   				for(int i=0;i<giocate;i++)
   				{
   					p1e1[i].setText("\n"+giocatore2);
   				//	p1e1[i].setBounds((0*i+10),0,100,50);
   					p2e1[i].setText("\n"+mosse1);
   				//	p1e1[i].setBounds((100*i+10),0,100,50);
   				}
   			}	
   			else
   			{ 
   					if(cont>cont2 && perso==1 && lastmove==1) 
   					{
						for(int i=0;i<giocate;i++)
   						{
   							p1e1[i].setText("\n"+giocatore2);
   				//			p1e1[i].setBounds(0*i+10,0,100,50);
   							p2e1[i].setText("\n"+mosse1);
   				//			p1e1[i].setBounds(100*i+10,0,100,50);
   						}
   					}
   			}
   			
   			
   			
   			   			
   		   
/*   			
   			try
   				{
   					 FileInputStream f = new FileInputStream(punti);
     				 ObjectInputStream fIN = new ObjectInputStream(f);
     				 
     				 giocatore1=(String)fIN.readObject();
     				 giocatore2=(String)fIN.readObject();
     				 mosse1=fIN.readInt();
     				 mosse2=fIN.readInt();
     				 giocate=fIN.readInt();
     			}
     			
     		catch (Exception e)
   				{
     				//Visual.message("Nessun punteggio disponibile, errore: "+e);
   				}
*/ 		     
   			
     		
     		//punti.getContentPane().add(sfond);
     		
     		
     		mio_contenitore.add(p1);
     		mio_contenitore.add(p2);
     		
     		p1.setBounds(0,0,100,50);
     		p2.setBounds(100,0,50,100);
     		
     		    		
     		mio_contenitore.setBackground(Color.red);
     		
     		//sfond.setVisible(false);
     		
     		punti.setBounds(100,100,800,600);
     		//punti.setResizable(false);
     		punti.setVisible(true);	     			
     		mio_contenitore.setVisible(true);
   		
 		}  
 	} // fine gestore score 
 
 	class gestoreArreso extends MouseAdapter 
 	{
 	
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{
   			if(turno==0)
   			Visual.message("*** Coniglio!!! ***\nHai lasciato vincere "+giocatore2);
   			if(turno==1)
   			Visual.message("*** Coniglio!!! ***\nHai lasciato vincere "+giocatore1);
   			
   			azzera();
   	
 		}   // fine gestoreArreso  
 	}
 
 	class gestoreEsci extends MouseAdapter 
 	{
 	
  		public void mouseClicked(MouseEvent oggettoEvento)
   		{
   	
	   		Visual.message("*** Grazie per aver giocato ***");
	   		System.exit(0);
	   		
   	
 		}   // fine gestore uscita
 	}
 	

 	public void azzera()
 	{
 		perso=0;
 		
 		px=0;
 		py=0;
 		
 		for(int i=0;i<griglia.length;i++)
  		{
  			for(int j=0;j<griglia[i].length;j++)
  			{
  				
  				griglia[i][j].setIcon(emptygrid);
  				mappa[i][j]=3;
  					
  			}
  		}
  	}

	public void giocatori()
	{
		giocatore1="Giocatore 1";

		giocatore1=JOptionPane.showInputDialog(giocatore1,"Giocatore 1");
		System.out.print("\n"+giocatore1);	
		
		giocatore2="Giocatore 2";
		giocatore2=JOptionPane.showInputDialog(giocatore2,"NoPc");
		System.out.print("\n"+giocatore2+"\n");
			 		
 	}
 	
/* 	
 	public void giocatore2()
 	{
 		int gett=0;
	//	Algoritmo mosse IA....implementazione in corso
	
		// controllo orrizzontali difesa
    	for(int a=0;a<mappa.length -1; a++)
    	{
     		for(int b=0; b<4; b++)
     		{
      			if(mappa[a][b]==0 && mappa[a][b+1]==0 && mappa[a][b+2]==0 && (mappa[a][b+1]=3 || mappa[a][b-1]=3))
      			{
      				
      			}
  	  		}
  	 
  		}
  	}
*/	
		

 	public int riga(int x)
 	{
  	int rigaz=-1;
  	if (x>240 && x<290) rigaz=0;
  	if (x>291 && x<342) rigaz=1;
  	if (x>343 && x<393) rigaz=2;
  	if (x>394 && x<444) rigaz=3;
  	if (x>445 && x<495) rigaz=4;
  	if (x>496 && x<546) rigaz=5;
  	 
  	System.out.print("\nx= "+x); 	 
  	return rigaz;	
 	}
 	
 	public int colonna(int y)
 	{
  	int col=-1;
  	if (y>60 && y<110) col=0;
  	if (y>111 && y<161) col=1;
  	if (y>162 && y<212) col=2;
  	if (y>213 && y<263) col=3;
  	if (y>264 && y<314) col=4;
  	if (y>315 && y<365) col=5;
  	if (y>366 && y<416) col=6;
  	System.out.print("\ny= "+y); 	 
  	return col;
 		
 	}
 	
 	public int vittoria()
 	{
 		
    // controllo orrizzontali
    for(int a=0;a<mappa.length -1; a++)
    {
     for(int b=0; b<4; b++)
     {
      if(mappa[a][b]==turno && mappa[a][b+1]==turno && mappa[a][b+2]==turno && mappa[a][b+3]==turno)
      {
      perso=1;
      save();
  	  return turno;
  	  }
  	 }
  	}
    
    // controllo verticali  	   
    for(int a=0;a<3; a++)
    {
     for(int b=0; b<mappa[a].length; b++)
     {
      if(mappa[a][b]==turno && mappa[a+1][b]==turno && mappa[a+2][b]==turno && mappa[a+3][b]==turno)
      {
      	perso=1;
      	save();
  	   	return turno;
  	  }
  	 }
  	}
  	
    // controllo diagonali principale 	   
    for(int a=0;a<3; a++)
    {
     for(int b=0; b<4; b++)
     {
      if(mappa[a][b]==turno && mappa[a+1][b+1]==turno && mappa[a+2][b+2]==turno && mappa[a+3][b+3]==turno){
      	perso=1;
      	save();
  	   return turno;}
  	 }
  	}  	
 
    // controllo diagonali secondarie  
    for(int a=3;a<6; a++)
    {
     for(int b=0; b<4; b++)
     {
      if(mappa[a][b]==turno && mappa[a-1][b+1]==turno && mappa[a-2][b+2]==turno && mappa[a-3][b+3]==turno){
      	perso=1;
      	save();
  	   return turno;}
  	 }
  	}  	 
 
  	return 0 ;
 		
 	}


	public void save()
	{
				try
    			{

     				FileOutputStream f = new FileOutputStream("score");
      				ObjectOutputStream fOUT = new ObjectOutputStream(f);
    
       				fOUT.writeInt(mosse1);
       				fOUT.writeInt(mosse2);
       				fOUT.writeInt(giocate);
       				
       				fOUT.writeObject(giocatore1);
       				fOUT.writeObject(giocatore2);
       				
      				fOUT.flush();
      				f.close();
      				
   				}
   				
   				catch(Exception eccez)
     			{ 
     				Visual.message("Non e' possibile salvare i punteggi per il seguente errore: " + eccez.getMessage());  
     			}
	}

}