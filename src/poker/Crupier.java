package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class Crupier.
 * 
 * @author David Henao Martinez - 1824899
 * @author Alejandro Pergüeza Amaya - 1870617
 */
public class Crupier
{
    
    /** The baraja completa es un ArryList con las 52 cartas completas de la baraja.
     * */
    private ArrayList<JLabel> barajaCompleta;
    
    /** The cartas mesa son las cartas que se encuentran en la mesa.
     * */
    protected ArrayList<JLabel> cartasMesa;
    
    /** The desempate es un ArrayList auxiliar donde almaceno las cartas de un jugador.
     * */
    private ArrayList<JLabel> desempate;
    
    /** The carta almacena una carta cualquiera.
     *  The card1 es la primera carta del jugador con el que comparare la jerarquia.
     *  The card2 es la segunda carta del jugador con el que comparare la jerarquia.
     *  the auxiliar es una carta cualquiera que almaceno para diversos usos.
     *  */
    private JLabel carta, card1, card2, auxiliar;
    
    /** The random. */
    private Random random;

    /** The pinta1 almaceno el string del palo1.
     *  The pinta2 almaceno el string del palo2.
     *  The pinta3 almaceno el string del palo3.
     *  The pinta4 almaceno el string del palo4.
     *  The pinta almaceno el string del palo que tenga que asignar.
     *  */
    private String pinta1, pinta2, pinta3, pinta4, pinta;
    
    /** The aleatorio es un numero aleatorio.
     *  The miscelaneo tiene diversos usos no especificos.
     *  the no Repetidos es el numero de cartas repetidas con el mismo valor.
     *  */
    private int aleatorio, miscelaneo, noRepetidos;
    
    /** The valores en mesa almacena los valores de las cartas de la mesa descartando su palo.
     *  The verificar escalera almacena los valores numericos de las cartas de la mesa y del jugador.
     *  The sin repetidos almacena los valores numericos que hay en verificar escalera pero sin repetidos (se reemplaza con 0 en caso de encontrar uno).
     *  The escalera de color almacena los valores numericos que tiene las cartas del mismo palo si hay mas de 5 del mismo palo.
     *  */
    private int[] valoresEnMesa, verificarEscalera, sinRepetidos, escaleraDeColor;
    
    /** The colores almacena los palos de las cartas con un respectivo caracter para cada uno de estos.
     *  The valor y pinta almacena las cartas con palo y valor. 
     *  */
    private String[] colores, valorYPinta;
    
    /** The trio determina si hay tres cartas del mismo valor numerico.
     *  The color determina si existen 5 cartas de color o no.
     *  The escalera determina si hay 5 cartas con valores numericos seguidos.
     *  The poker determinan si hay 4 cartas del mismo valor numerico.
     *  The escalera color determina si hay una escalera de color o no.
     *  The escalera real determina si hay una escalera real o no.
     *  The color seguido determina si tengo cartas con los colores y sus respectivos valores organizados de manera ascendente (los palos tambien).
     *  The as determina si hay una as en la mesa o en la mano del jugador.
     *  The k determina si hay una k en la mesa o en la mano del jugador.
     *  */
    private boolean trio, color, escalera, poker, escaleraColor, escaleraReal, colorSeguido, As, K;

    /**
     * Instantiates a new crupier.
     */
    public Crupier() {

        barajaCompleta = new ArrayList<>();
        cartasMesa = new ArrayList<>();
        desempate = new ArrayList<>();
        random = new Random();
        
        valoresEnMesa = new int[5];
        verificarEscalera = new int[7];
        sinRepetidos = new int[7];
        escaleraDeColor = new int[7];
        
        pinta1 = "corazon ";
        pinta2 = "diamante ";
        pinta3 = "pica ";
        pinta4 = "trebol ";
        pinta = "";
        
        colores = new String[7];
        valorYPinta = new String[7];
    }

    /**
     * Iniciar baraja.
     * 
     * Organiza el mazo con las 52 cartas y lo almacena.
     */
    public void iniciarBaraja() {

        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0:
                    pinta = pinta1;
                    break;
                case 1:
                    pinta = pinta2;
                    break;
                case 2:
                    pinta = pinta3;
                    break;
                case 3:
                    pinta = pinta4;
                    break;
            }
            for(int j = 1; j <= 13; j++) {
                carta = new JLabel(new ImageIcon("src/mazo/" + pinta + "(" + j + ").png"));
                carta.setName(pinta.substring(0, 1) + j);
                barajaCompleta.add(carta);
            }
        }
    }

    /**
     * Retirar carta.
     *
     * Se toma una carta aleatoria de la baraja completa.
     *
     * @return the j label que es la carta que se retiro de la baraja completa.
     */
    public JLabel retirarCarta() {
        aleatorio = random.nextInt(barajaCompleta.size());

        carta = barajaCompleta.get(aleatorio);
        barajaCompleta.remove(aleatorio);
        return carta;
    }


    /**
     * Abrir mesa.
     * 
     * Toma tres cartas de la baraja completa y las pone en la mesa.
     */
    public void abrirMesa() {

        cartasMesa.add(retirarCarta());
        cartasMesa.add(retirarCarta());
        cartasMesa.add(retirarCarta());
    }

    /**
     * Adds the to mesa.
     * 
     * Toma una carta de la baraja completa y la coloca en la mesa.
     *
     * @return the j label que es la carta que se saco para la mesa (La retornamos para poder graficarla).
     */
    public JLabel addToMesa(){
        carta = this.retirarCarta();
        cartasMesa.add(carta);
        return carta;
    }

    /**
     * Cerrar mesa.
     * 
     * Elimina todas las cartas que hay en la mesa (las devuelve al mazo).
     * Elimina todas las cartas que hay en la baraja completa (Toma las cartas y vuelve a revolver el mazo).
     */
    public void cerrarMesa() {

        cartasMesa.removeAll(cartasMesa);
        barajaCompleta.removeAll(barajaCompleta);
    }


    /**
     * Decision de jerarquia.
     * retorna un numero dependiendo de la jerarquia de victoria del poker como se describe a continuacion:
     *
     * @param player, las cartas que posee el jugador con el cual se determinara su jerarquia.
     * 
     * @return the int:
     * - 1 si es carta alta.
     * - 2 si es pareja.
     * - 3 si es doble pareja.
     * - 4 si es trio.
     * - 5 si es escalera.
     * - 6 si es color (cicnco cartas del mismo color).
     * - 7 si es un Full House.
     * - 8 si es un Poker.
     * - 9 si es una escalera de color.
     * - 10 si es una escalera real.
     */
    public int decisionDeJerarquia(ArrayList<JLabel> player) {

        card1 = player.get(0);
        card2 = player.get(1);

        String color1 = card1.getName().substring(0, 1);
        String color2 = card2.getName().substring(0, 1);
        String nameAux = "";
        int valor1 = 0;
        int valor2 = 0;

        if((card1.getName().length()) == 3) {
            valor1 = Integer.parseInt((card1.getName().substring(1, 3)));
        }
        else
            valor1 = Integer.parseInt((card1.getName().substring(1, 2)));

        if(card2.getName().length() == 3)
            valor2 = Integer.parseInt(card2.getName().substring(1, 3));
        else
            valor2 = Integer.parseInt(card2.getName().substring(1, 2));

        noRepetidos = 0;

        trio = false;
        color = false;
        escalera = false;
        poker = false;
        escaleraColor = false;
        escaleraReal = false;
        colorSeguido = false;
        As = false;
        K = false;



        for(int i = 0; i < cartasMesa.size(); i++) {
            auxiliar = cartasMesa.get(i);
            nameAux = auxiliar.getName();
            valorYPinta[i] = nameAux;
            if(nameAux.length() == 3)
                verificarEscalera[i] = Integer.parseInt(nameAux.substring(1, 3));
            else
                verificarEscalera[i] = Integer.parseInt(nameAux.substring(1, 2));
            if(verificarEscalera[i] == 1)
                As = true;
            else if(verificarEscalera[i] == 13)
                K = true;
            valoresEnMesa[i] = verificarEscalera[i];
        }

        verificarEscalera[5] = valor1;
        verificarEscalera[6] = valor2;
        valorYPinta[5] = card1.getName();
        valorYPinta[6] = card2.getName();
        Arrays.parallelSort(verificarEscalera);
        Arrays.parallelSort(valoresEnMesa);
        Arrays.parallelSort(valorYPinta);

        sortByValue(verificarEscalera, valorYPinta);

        int repeticiones = 1;
        int iteraciones = 1;
        int nCeros = 0;

        for(int i = 0; i < 6; i++) {

            if(verificarEscalera[i] == verificarEscalera[i+1]) {
                iteraciones++;
                nCeros++;
                if(iteraciones >= repeticiones)
                    repeticiones = iteraciones;
            }
            else {
                sinRepetidos[noRepetidos] = verificarEscalera[i];
                noRepetidos++;
                iteraciones = 1;
            }
            if(i+1 == 6) {
                sinRepetidos[noRepetidos] = verificarEscalera[i+1];
                noRepetidos++;
                if(iteraciones >= repeticiones)
                    repeticiones = iteraciones;
            }
        }

        if(repeticiones == 4) {
            poker = true;
        }
        if(repeticiones == 3) {
            trio = true;
        }

        Arrays.parallelSort(sinRepetidos);


        if(valor1 == 1 || valor2 == 1)
            As = true;
        if(valor1 == 13 || valor2 == 13)
            K = true;

        for(int i = 0; i < cartasMesa.size(); i++) {
            auxiliar = cartasMesa.get(i);
            nameAux = auxiliar.getName();

            colores[i] = nameAux.substring(0, 1);
        }

        colores[5] = color1;
        colores[6] = color2;





        //Evaluar si es posible obtener la condicion de Color:

        String laLetra = "";
        repeticiones = 0;
        iteraciones = 0;

        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0:
                    pinta = "t";
                    break;
                case 1:
                    pinta = "p";
                    break;
                case 2:
                    pinta = "d";
                    break;
                case 3:
                    pinta = "c";
                    break;
            }
            for(int j = 0; j < 7; j++) {
                if(pinta.equals(colores[j])) {
                    iteraciones++;
                }
            }
            if(iteraciones >= repeticiones) {
                repeticiones = iteraciones;
                laLetra = pinta;
            }
            iteraciones = 0;
        }

        if(repeticiones >= 5)
            color = true;


        //Obtener Array con las cartas de la misma pinta (Solo cuando hay color):


        if(color) {

            for(int i = 0; i < 7; i++) {

                if(laLetra.equals(valorYPinta[i].substring(0, 1))) {
                    if(valorYPinta[i].length() == 2)
                        escaleraDeColor[i] = Integer.parseInt(valorYPinta[i].substring(1, 2));
                    else {
                        escaleraDeColor[i] = Integer.parseInt(valorYPinta[i].substring(1, 3));
                    }
                }
            }

            Arrays.parallelSort(escaleraDeColor);
        }


        //Verificar que tenga escalera de color:

        int contador = 1;
        int KIzq = 0;
        int contadorAuxiliar = 1;

        boolean AsAux = false;
        boolean KAux = false;

        if(color) {

            for(int i = 0; i < 7; i++) {

                if(escaleraDeColor[i] == 1)
                    AsAux = true;
                if(escaleraDeColor[i] == 13)
                    KAux = true;
            }

            if(AsAux && KAux) {

                contadorAuxiliar = 1;
                contador = 2;
                KIzq = 0;

                for(int i = 0; i < 6; i++) {
                    if(escaleraDeColor[i] == 1 || escaleraDeColor[i] == 0) {}
                    else {
                        if(escaleraDeColor[i]-contador == 0)
                            contador++;
                        else
                            break;
                    }
                }

                for(int i = 6; i >= 0; i--) {
                    if(escaleraDeColor[i] == 13) {}
                    else {
                        if(escaleraDeColor[i]+contadorAuxiliar == 13) {
                            contadorAuxiliar++;
                            KIzq++;
                        }
                        else
                            break;
                    }
                }

                if(contador+KIzq >= 5)
                    colorSeguido = true;

            }
            else {

                contador = 1;
                for(int i = 0; i < 6; i++) {
                    if(escaleraDeColor[i] == 0) {}
                    else {
                        if(escaleraDeColor[i] == escaleraDeColor[i+1]-1) {
                            contador++;
                        }
                        else {
                            if(contador >= 5) {
                                colorSeguido = true;
                            }
                            else {
                                if(escaleraDeColor[i] == escaleraDeColor[i+1]) {}
                                else
                                    contador = 1;
                            }
                        }
                    }
                    if(contador >= 5) {
                        colorSeguido = true;
                    }
                }
            }
        }



        //Verificar si tiene victoria por escalera:

        if(As && K) {

            contadorAuxiliar = 1;
            contador = 2;
            KIzq = 0;

            for(int i = 0; i < 6; i++) {
                if(verificarEscalera[i] == 1) {}
                else {
                    if(verificarEscalera[i]-contador == 0)
                        contador++;
                    else
                        break;
                }
            }

            for(int i = 6; i >= 0; i--) {
                if(verificarEscalera[i] == 13) {}
                else {
                    if(verificarEscalera[i]+contadorAuxiliar == 13) {

                        if(verificarEscalera[i-1]+contadorAuxiliar == 13) {}
                        else {
                            contadorAuxiliar++;
                            KIzq++;
                        }
                    }
                    else
                        break;
                }
            }

            if(KIzq >= 3 && colorSeguido && AsAux && KAux) {
                escaleraReal = true;
            }
            else if(contador+KIzq >= 5 && colorSeguido)
                escaleraColor = true;
            else if(contador+KIzq >= 5)
                escalera = true;
        }
        else {

            contador = 1;
            for(int i = 0; i < 6; i++) {
                if(verificarEscalera[i] == verificarEscalera[i+1]-1) {
                    contador++;
                }
                else {
                    if(contador >= 5) {
                        if(colorSeguido) {
                            escaleraColor = true;
                        }
                        else
                            escalera = true;
                        break;
                    }
                    else {
                        if(verificarEscalera[i] == verificarEscalera[i+1]) {}
                        else
                            contador = 1;
                    }
                }

                if(i+1 == 6 && contador >= 5) {
                    if(colorSeguido) {
                        escaleraColor = true;
                    }
                    else
                        escalera = true;
                }
            }
        }

        
        
		//Reinicio de Arrays.

        for(int i = 0; i < 7; i++) {
            verificarEscalera[i] = 0;
        }

        for(int i = 0; i < 5; i++) {
            valoresEnMesa[i] = 0;
        }

        for(int i = 0; i < 7; i++) {
            sinRepetidos[i] = 0;
        }

        for(int i = 0; i < 7; i++) {
            escaleraDeColor[i] = 0;
        }

        for(int i = 0; i < 7; i++) {
            colores[i] = "";
        }

        for(int i = 0; i < 7; i++) {
            valorYPinta[i] = "";
        }
        
        
        
        // Evalua las condiciones de victoria y retorna su Jerarquia si se cumplen

        if(escaleraReal)
            return 10;

        else if(escaleraColor)
            return 9;

        else if(poker)
            return 8;

        else if(trio && nCeros >= 3)
            return 7;

        else if(color)
            return 6;

        else if(escalera)
            return 5;

        else if(trio)
            return 4;

        else if(nCeros >= 2)
            return 3;

        else if(nCeros == 1)
            return 2;

        else 
            return 1;
    }

    /**
     * Sort by value.
     * 
     * Organiza un array de cartas con su palo y valor de manera ascendente respecto a sus valores.
     *
     * @param numeros the numeros es el array que contiene los valores numericos de las cartas de la mesa y el jugador ordenados ascendentemente.
     * @param string the string es el array valor y pinta que  almacena las cartas con palo y valor organizados alfabeticamente.
     */
    private void sortByValue(int[] numeros, String[] string) {

        String[] nuevo = new String[7];
        int aux = 0;

        for(int i = 0; i < 7; i++) {

            for(int j = 0; j < 7; j++) {

                if(string[j].length() == 3)
                    aux = Integer.parseInt(string[j].substring(1, 3));
                else
                    aux = Integer.parseInt(string[j].substring(1, 2));
                if(numeros[i] == aux) {
                    nuevo[i] = string[j];
                    string[j] = "g99";
                    break;
                }
            }
        }

        for(int i = 0; i < 7; i++) {
            string[i] = nuevo[i];
        }
    }

    /**
     * Decidir ganador.
     * 
     * Obtiene las jerarquias de victoria de cada uno de los ganadores y determina quien gana en base a estas.
     * En caso de empate, se llama a la funcion resolverDraw que se encarga de determinar el ganador del empate,
     * o bien si los jugadores empatados quedan en empate decisivo.
     *
     * @param jugadores the jugadores es el ArrayList de los jugadores.
     * 
     * @return the int retorna el ganador, o bien los ganadores en caso de empate decisivo.
     */
    public int decidirGanador(ArrayList<Jugador> jugadores)
    {
        int ganador = 0;
        int nJugador = jugadores.size(), puntajeMayor=0, empatan=0;
        int[] puntuacion = new int[nJugador];
        int[] empatados = new int[nJugador];
        
        for(int i=0; i<nJugador; i++)
        {
            puntuacion[i] = decisionDeJerarquia(jugadores.get(i).getBarajaMia());
            if(puntajeMayor < puntuacion[i]) {
                puntajeMayor = puntuacion[i];
                ganador = i+1;
            }
        }
        
        for(int i=0; i<nJugador; i++)
        {
            if(puntajeMayor == puntuacion[i])
            {
            	empatados[empatan] = i;
                empatan++;
            }
        }
        
        if(empatan>1)
        {
            ganador = resolveDraw(empatan, jugadores);
        }
        
        return ganador;
    }
    
    /**
     * Resolve draw.
     * 
     * Descubre quien es el ganador en caso de la carta alta, o la segunda carta mas alta,
     * o bien si ambos tiene las mismos valores numericos en sus cartas se retorna a los ganadores.
     *
     * @param nEmpatados the n empatados es el numero de jugadores en empate.
     * @param jugadores the jugadores son los jugadores a quienes se deterinara su desempate.
     * @return the int que me dicat el ganador o bien los ganadores.
     */
    private int resolveDraw(int nEmpatados, ArrayList<Jugador> jugadores) {
    	
    	int ganador = 0;
    	double losGanadores = 0;
    	int contador = 0;
    	int valorMaximo = 0;
    	int neoValorMaximo = 0;
    	int busqueda = 0;
    	int concurrencias = 0;
    	int neoConcurrencias = 0;
    	int[] cartasGanadores = new int[nEmpatados*2];
    	int[] aDesempatar;
    	int[] dobleDesempate;
    	int[] empateDecisivo;
    	int[] cartasGanadoresOriginal = new int[nEmpatados*2];
    	miscelaneo = 0;
    	
    	
    	for(int i = 0; i < nEmpatados; i++) {
    		
    		desempate = jugadores.get(i).getBarajaMia();
    		for(int j = 0; j < 2; j++) {
    			
    			auxiliar = desempate.get(j);

    	        if((auxiliar.getName().length()) == 3) {
    	        	miscelaneo = Integer.parseInt((auxiliar.getName().substring(1, 3)));
    	        }
    	        else
    	        	miscelaneo = Integer.parseInt((auxiliar.getName().substring(1, 2)));
    	        
    	        
    	        if(miscelaneo == 1) {
    	        	cartasGanadoresOriginal[contador] = 14; // representacion numerica del As.
        	        cartasGanadores[contador] = 14;
    	        }
    	        else {
	    	        cartasGanadoresOriginal[contador] = miscelaneo;
	    	        cartasGanadores[contador] = miscelaneo;
    	        }
    	        contador++;
    		}
    	}
    	
    	Arrays.parallelSort(cartasGanadores);
    	
    	valorMaximo = cartasGanadores[(nEmpatados*2)-1];
    	
    	//Decision de ganador por primera carta maxima:
    	
    	concurrencias = 1;
    	
    	for(int i = (nEmpatados*2)-2; i >= 0; i--) {
    		
    		if(cartasGanadores[i] == valorMaximo)
    			concurrencias++;
    		else
    			break;
    	}
    	
    	
    	if(concurrencias == 1) {
    		//Sale como un solo ganador, y se encuentra a dicho ganador
    	
    		for(int i = 0; i < (nEmpatados*2); i++) {
    			
    			if(valorMaximo == cartasGanadoresOriginal[i]) {
    				ganador = (i/2) +1;
    				return ganador;
    			}
    		}
    		
    	}
    	
    	
    	
    	//Decision de ganador por segunda carta:
    	
    	if(concurrencias > 1) {
    		
    		contador = 0;
    		aDesempatar = new int[concurrencias];
    		
    		for(int i = 0; i < (nEmpatados*2)-1; i++) {
    			
    			if(valorMaximo == cartasGanadoresOriginal[i]) {
    				
    				if(contador == 0) {
	    				aDesempatar[contador] = (i/2) + 1; //Esto me indica el jugador que obtuvo la carta de mayor puntaje
	    				contador++;
    				}
    				else {
    					
    					if(aDesempatar[contador-1] == ((i/2) + 1)) {}
    					else {
	    					aDesempatar[contador] = (i/2) + 1;
		    				contador++;
    					}
    				}
    			}
    		}
    		
    		//Falta verificar por segunda carta :'v
    		
    		
    		dobleDesempate = new int[contador];
    		
    		for(int i = 0; i < contador; i++) {
    			
    			busqueda = aDesempatar[i];
    			
    			if(cartasGanadoresOriginal[(busqueda*2) -1] == valorMaximo)
    				dobleDesempate[i] = cartasGanadoresOriginal[(busqueda*2) -2];
    			else
    				dobleDesempate[i] = cartasGanadoresOriginal[(busqueda*2) -1];
    			
    			if(dobleDesempate[i] >= neoValorMaximo)
    				neoValorMaximo = dobleDesempate[i];
    		}
    		
    		
    		neoConcurrencias = 0;
    		empateDecisivo = new int[contador];
    		for(int i = 0; i < contador; i++) {
    			
    			if(dobleDesempate[i] == neoValorMaximo) {
    				empateDecisivo[i] = aDesempatar[i];
    				neoConcurrencias++;
    			}
    		}
    		
    		if(neoConcurrencias == 1) {
    			
    			for(int i = 0; i < contador; i++) {
    				
    				if(empateDecisivo[i] > 0) {
    					ganador = empateDecisivo[i];
    					return ganador;
    				}
    			}
    			
    		}
    		else {
    			
    			for(int i = 0; i < contador; i++) {
    				
    				int potencia = contador-i-1;
    				losGanadores = Math.pow(10, potencia);
    				ganador += empateDecisivo[i]*((int) losGanadores);
    			}
    			return ganador;
    			
    		}
    		
    		
    	}
    	
    	return ganador;
    }

}
