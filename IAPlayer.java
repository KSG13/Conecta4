/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta4;

import java.util.ArrayList;
import javafx.util.Pair;


public class IAPlayer extends Player 
{

    static long n_nodos;
    
    public class Nodo
    {
        private int[][] contenido; //Matriz del tablero
        private final ArrayList<Nodo> NodosHijos; //Hijos del tablero
        boolean extender = true; //Si es una victoria, no tiene sentido seguir extendiendo
        
        boolean minimax; //FALSE -> MIN , TRUE ->MAX
        int heuristica; //VALOR HEURISTICO DEL NODO
        
        public Nodo(int[][] contenido) {
            NodosHijos = new ArrayList();
            this.contenido = contenido;
        }

        public boolean isMinimax() {
            return minimax;
        }

        public void setMinimax(boolean minimax) {
            this.minimax = minimax;
        }

        public int getHeuristica() {
            return heuristica;
        }

        public void setHeuristica(int heuristica) {
            this.heuristica = heuristica;
        }
        
        public int[][] getContenido() {
            return contenido;
        }

        void setExtension( boolean valor )
        {extender=valor;}
        
        boolean getExtension()
        {return extender;}
        
        void setContenido( int[][] m) {
            contenido = m;
        }
        
        public ArrayList<int[][]> getNodosHijosMatriz() {
            
            ArrayList<int[][]> hijos = new ArrayList();
            
            for(int i = 0; i<NodosHijos.size(); i++)
            {
                hijos.add(NodosHijos.get(i).getContenido());
            }
            
            return hijos;
        }
        
        public ArrayList<Nodo> getNodosHijos() { return NodosHijos; }
        
        void setNodosHijos( ArrayList<int[][]> hijos , ArrayList<Integer> poda) {

            boolean insertar = true;
            
            for(int i = 0; i<hijos.size(); i++)
            {
                insertar = true;
                for(int j = 0; j<poda.size(); j++)
                {
                    if(poda.get(j)==i)
                    {insertar = false;}
                }
                if(insertar)
                {
                    Nodo n = new Nodo(hijos.get(i));
                    n.setExtension(true);
                    NodosHijos.add(n);  
                }     
                else
                {
                    Nodo n = new Nodo(hijos.get(i));
                    n.setExtension(false);
                    NodosHijos.add(n);  
                }
            }
  
        }

    }
    
    boolean Vertical( int[][] m , int filas , int columnas , int conecta )
    {
        boolean vertical = false;
        
        //LA COMPROBACIÓN EN VERTICAL, ES LA MÁS SENCILLA, YA QUE SOLO TENEMOS
        //QUE MOVERNOS EN LA FILA DE ABAJO DEL TODO EN EL CASO DEL 4X4
        
        //NÚMERO DE FILAS - (FILA ACTUAL + 1) = NUMERO DE CASILLAS POR ENCIMA CONTANDO LA ACTUAL
        
        int referenciaIA = conecta;
        int referenciaJG = conecta;
        
        for(int i = 0; i<columnas; i++)
        {
            referenciaJG = conecta;
            referenciaIA = conecta;
            
            for(int j = 0; j<filas; j++)
            {
                if(m[j][i]!=0)//Movimiento en vertical
                {
                    if(m[j][i]==1)
                    {
                        referenciaJG--;
                    }
                    if(m[j][i]==-1)
                    {
                        referenciaIA--;
                    }
                    if( referenciaJG==0 || referenciaIA==0 )
                    {vertical=true;}
                }
                else//Si no son seguidos se reinicia
                {
                    referenciaJG = conecta;
                    referenciaIA = conecta;
                }
            }
        }
        
        
        return vertical;
    }
    
    boolean Horizontal( int[][] m , int filas , int columnas , int conecta )
    {
        boolean horizontal = false;
        
        //LA COMPROBACIÓN EN VERTICAL, ES LA MÁS SENCILLA, YA QUE SOLO TENEMOS
        //QUE MOVERNOS EN LA FILA DE ABAJO DEL TODO EN EL CASO DEL 4X4
        
        //NÚMERO DE FILAS - (FILA ACTUAL + 1) = NUMERO DE CASILLAS POR ENCIMA CONTANDO LA ACTUAL
        
        int referenciaIA = conecta;
        int referenciaJG = conecta;
        
        for(int i = 0; i<filas; i++)
        {
            referenciaJG = conecta;
            referenciaIA = conecta;
            
            for(int j = 0; j<columnas; j++)
            {
                if(m[i][j]!=0)//Movimiento en horizontal
                {
                    if(m[i][j]==1)
                    {
                        referenciaJG--;
                    }
                    if(m[i][j]==-1)
                    {
                        referenciaIA--;
                    }
                    if( referenciaJG==0 || referenciaIA==0 )
                    {horizontal=true;}
                }
                else//Si no son seguidos se reinicia
                {
                    referenciaJG = conecta;
                    referenciaIA = conecta;
                }
            }
        }
        
        
        return horizontal;
    }
    
    boolean Diagonal( int[][] m , int filas , int columnas , int conecta )
    {
        boolean diagonal = false;
        
        int referenciaIA = conecta;
        int referenciaJG = conecta;
        
        //Recorremos por diagonales la matriz
        for (int i=0;i<filas;i++) 
        {
            referenciaIA = conecta;
            referenciaJG = conecta;
            for (int j=0;j<=i;j++) 
            { 
                if(m[i-j][j]==1)
                {referenciaJG--;}
                if(m[i-j][j]==-1)
                {referenciaIA--;}
                if( referenciaJG==0 || referenciaIA==0 )
                {diagonal=true;}
            }
        }
        
        for (int i=columnas-1;i>=0;i--) 
        {
            referenciaIA = conecta;
            referenciaJG = conecta;
            for (int j=filas-1;j>=i;j--) 
            { 
                if(m[j-i][j]==1)
                {referenciaJG--;}
                if(m[j-i][j]==-1)
                {referenciaIA--;}
                if( referenciaJG==0 || referenciaIA==0 )
                {diagonal=true;}
            }
        }
        
        return diagonal;
    }
    
    boolean EsVictoria( int[][] m , int filas , int columnas , int conecta )
    {

        boolean victoria = false;
        
        boolean vertical = Vertical( m , filas , columnas , conecta );
        boolean horizontal = Horizontal( m , filas , columnas , conecta );
        boolean diagonal = Diagonal( m , filas , columnas , conecta );
        
        if( vertical || horizontal || diagonal )
        {victoria = true;}
        
        return victoria;
    }
    
    ArrayList<int[][]> DevuelveHijosIA( int[][] m , int filas , int columnas , int disponibles )
    {
        ArrayList<int[][]> hijos = new ArrayList();
        int[][] hijo = new int[6][7];
        //El número de hijos posibles, es el número de columnas menos el 
        //número de columnas completas.
        int n = disponibles;
        
        while(n>0)
        {
            for(int i = filas-1; i>=0; i--)
            {
                for(int j = 0; j<columnas; j++)
                {
                    if(i==filas-1 && n>0 && m[i][j]==0)
                    {
                        hijo = CopiaMatriz(m);
                        hijo[i][j] = -1;
                        hijos.add(hijo);
                        n--;
                    }
                    else
                    {
                        if(m[i][j]==0 && m[i+1][j]!=0 && n>0)
                        {
                            hijo = CopiaMatriz(m);
                            hijo[i][j] = -1;
                            hijos.add(hijo);
                            n--;
                        }
                    }
                }
            }
        }
        
        return hijos;
    }
    
    ArrayList<int[][]> DevuelveHijosJugador( int[][] m , int filas , int columnas , int disponibles )
    {
        ArrayList<int[][]> hijos = new ArrayList();
        int[][] hijo;
        //El número de hijos posibles, es el número de columnas menos el 
        //número de columnas completas.
        int n = disponibles;
        
        while(n>0)
        {
            for(int i = filas-1; i>=0; i--)
            {
                for(int j = 0; j<columnas; j++)
                {
                    if(i==filas-1 && n>0 && m[i][j]==0)
                    {
                        hijo = CopiaMatriz(m);
                        hijo[i][j] = 1;
                        hijos.add(hijo);
                        n--;
                    }
                    else
                    {
                        if(m[i][j]==0 && m[i+1][j]!=0 && n>0)
                        {
                            hijo = CopiaMatriz(m);
                            hijo[i][j] = 1;
                            hijos.add(hijo);
                            n--;
                        }
                    }
                }
            }
        }
        
        return hijos;
    }
    
    int[][] CopiaMatriz( int[][] m )
    {
        int[][] retorno = new int[6][7];
        
        for(int i = 0; i<6; i++)
        {
            for(int j = 0; j<7; j++)
            {
                retorno[i][j] = m[i][j];
            }
        }
        
        return retorno;
    }
    
    int ColumnasDisponibles( int[][] m , int filas , int columnas )
    {
        int tam = columnas;
        
        for(int i = 0; i<columnas; i++)
        {
            if(m[0][i]!=0)
            {tam--;}
        }
        
        return tam;
    }
    
    void MuestraTablero( int[][] m  , int filas , int columnas)
    {
        for(int i = 0; i<filas; i++)
        {
            for(int j = 0; j<columnas; j++)
            {   
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    void MuestraProfundidadUno( ArrayList<int[][]> TurnoIA , int filas , int columnas )
    {
        
        int profundidad_limite = 1;
        int heuristica;
        System.out.println(" --- TURNO IA --- ");
        System.out.println(" NIVEL / PROFUNDIDAD : " + profundidad_limite);
        int conecta = 4;
        for(int i = 0; i<TurnoIA.size(); i++)
        {
            System.out.println(" COMBINACIÓN Nº : " + (i + 1));
            System.out.println("VALOR HEURISTICO: " + ValorHeuristico(TurnoIA.get(i),filas,columnas,conecta));
            MuestraTablero( TurnoIA.get(i) , filas , columnas);
        }
    }
    
    void MuestraProfundidadDos( ArrayList<ArrayList<int[][]>> apoyo2 , int filas , int columnas )
    {
        int profundidad_limite = 2;
        int conecta = 4;
        System.out.println(" --- TURNO JUGADOR --- ");
        System.out.println(" NIVEL / PROFUNDIDAD " + profundidad_limite);
        for(int i = 0; i<apoyo2.size(); i++)
        {
            System.out.println("NODO HIJO Nº " + i);
            for(int j = 0; j<apoyo2.get(i).size(); j++)
            {
                System.out.println(" COMBINACIÓN Nº " + (j + 1) + "DEL NODO HIJO Nº" + (i + 1) );
                System.out.println("VALOR HEURISTICO: " + ValorHeuristico(apoyo2.get(i).get(j),filas,columnas,conecta));
                MuestraTablero( apoyo2.get(i).get(j) , filas , columnas);
            } 
        }
    }
    
    Nodo GenerarArbol( Grid tablero , int profundidad_limite)
    {
        //COMO EL PRMERO EN COLOCAR FICHA SIEMRPE ES EL JUGADOR, EL ORDEN
        //DEL ARBOL MINIMAX, SERÁ JUGADOR-IA-JUGADOR-IA...
        //COMO LA IA TIENE EL SEGUNDO TURNO, NO PODEMOS PARTIR DE UN ARBOL
        //CUYA RAIZ ES UN TABLERO VACIO. EN NUESTRO CASO LA RAIZ SERA
        //EL PRIMER MOVIMIENTO DEL JUGADOR
        //Y POR LO TANTO, LAS HOJAS SERÁN SOLUCIONES ÚNICAS EN EL TURNO DE LA IA
        
        // ¿ POR QUÉ NOS COMPLICAMOS LA VIDA USANDO MATRICES EN LUGAR DE TABLEROS ?
        // PORQUE EN LAS MATRICES TENEMOS DEFINIDO EL OPERADOR DE IGUALDAD
        // Y OTRAS FUNCIONES QUE CONOCEMOS MEJOR
        
        //SE ESTABLECE UN LIMITE DE NIVELES A DESARROLLAR EN EL ÁRBOL, YA QUE
        //INTENTAR DESARROLLAR EL ÁRBOL ENTERO SERÍA UNA LOCURA
        
        int[][] m = tablero.toArray();
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        int disponibles = ColumnasDisponibles( m , filas , columnas );
        int conecta = 4;
        int heuristica;
        
        Nodo n = new Nodo(m);
        heuristica = ValorHeuristico(m,filas,columnas,conecta);
        n.setHeuristica(heuristica);
        
        ArrayList<Integer> poda = new ArrayList();
        
        //Turno IA
        ArrayList<int[][]> TurnoIA = DevuelveHijosIA(  m , filas , columnas , disponibles );
        n.setNodosHijos(TurnoIA,poda);
        
        if(profundidad_limite==1)
        {
//            MuestraProfundidadUno( TurnoIA , filas , columnas );
        }
        
        ArrayList<Nodo> hijos = n.getNodosHijos();
        ArrayList<int[][]> hijos_Matriz = n.getNodosHijosMatriz();
        for(int i = 0; i<hijos.size(); i++)
        {
            heuristica = ValorHeuristico(hijos_Matriz.get(i),filas,columnas,conecta);
            hijos.get(i).setHeuristica(heuristica);
        }
        
        ArrayList<ArrayList<Nodo>> apoyo = new ArrayList();
        ArrayList<ArrayList<int[][]>> apoyo2 = new ArrayList();
        
        //Turno Jugador  
        for(int i = 0; i<hijos.size(); i++)
        {
            disponibles = ColumnasDisponibles( hijos_Matriz.get(i) , filas , columnas );
            ArrayList<int[][]> jugador = DevuelveHijosJugador(  hijos_Matriz.get(i) , filas , columnas , disponibles );
            hijos.get(i).setNodosHijos(jugador,poda);
            apoyo.add(hijos.get(i).getNodosHijos());
            apoyo2.add(hijos.get(i).getNodosHijosMatriz());
            for( int j = 0; j<apoyo.get(i).size(); j++)
            {
                heuristica = ValorHeuristico(apoyo2.get(i).get(j),filas,columnas,conecta);
                apoyo.get(i).get(j).setHeuristica(heuristica);
            }
        }
        
        ///////////////////////////////////////////////////////////////////// POR AQUI TE HAS QUEDADO
        //APLICANDO EL VALOR HEURISTICO A LOS NODOS
        
        if(profundidad_limite==2)
        {
//            MuestraProfundidadUno( TurnoIA , filas , columnas );
//            MuestraProfundidadDos( apoyo2 , filas , columnas );
        }
         

        ArrayList<ArrayList<Nodo>> support = new ArrayList();
        ArrayList<ArrayList<int[][]>> support_matrix = new ArrayList();
        
         
        if(profundidad_limite>2)
        {
//            MuestraProfundidadUno( TurnoIA , filas , columnas );
//            MuestraProfundidadDos( apoyo2 , filas , columnas );
            int contador = 2;
            
            while(contador!=profundidad_limite || contador<profundidad_limite)
            {
                //Turno IA
                for(int i = 0; i<apoyo.size(); i++)
                {
                    for(int j = 0; j<apoyo.get(i).size(); j++)
                    {
                        disponibles = ColumnasDisponibles( apoyo2.get(i).get(j) , filas , columnas );
                        ArrayList<int[][]> IA = DevuelveHijosIA( apoyo2.get(i).get(j) , filas , columnas , disponibles );
                        for(int k = 0; k<IA.size(); k++)
                        {
                            if(EsVictoria( IA.get(k), filas , columnas , conecta ))//SI UNO DE LOS HIJOS GENERADOS ES CONDICIÓN DE VICTORIA, NO HAY QUE EXTENDER ESA RAMA DEL ARBOL
                            {
                                poda.add(k);
                            }
                        }
                        apoyo.get(i).get(j).setNodosHijos(IA,poda);
                        support.add(apoyo.get(i).get(j).getNodosHijos());
                        support_matrix.add(apoyo.get(i).get(j).getNodosHijosMatriz());
                        for(int l = 0; l<support.size(); l++)
                        {
                            for(int t = 0; t<support.get(l).size(); t++)
                            {
                                heuristica = ValorHeuristico(support_matrix.get(l).get(t),filas,columnas,conecta);
                                support.get(l).get(t).setHeuristica(heuristica);
                            } 
                        }
                        poda.clear();
                    }
                }

//                System.out.println(" --- TURNO IA --- ");
//                System.out.println(" NIVEL / PROFUNDIDAD " + contador);
//                for(int i = 0; i<support_matrix.size(); i++)
//                {
//                    System.out.println("NODO HIJO Nº " + i);
//                    for(int j = 0; j<support_matrix.get(i).size(); j++)
//                    {
//                        System.out.println(" COMBINACIÓN Nº " + (j + 1) + "DEL NODO HIJO Nº" + (i + 1));
//                        System.out.println("VALOR HEURISTICO: " + ValorHeuristico(support_matrix.get(i).get(j),filas,columnas,conecta));
//                        MuestraTablero( support_matrix.get(i).get(j)  , filas , columnas);
//                    }
//                }

                contador++;
                if(contador==profundidad_limite || contador>profundidad_limite)
                {break;}

                apoyo.clear();
                apoyo2.clear();

                //Turno Jugador
                for(int i = 0; i<support.size(); i++)
                {
                    for(int j = 0; j<support.get(i).size(); j++)
                    {
                        disponibles = ColumnasDisponibles( support_matrix.get(i).get(j) , filas , columnas );
                        ArrayList<int[][]> Jugador = DevuelveHijosJugador( support_matrix.get(i).get(j) , filas , columnas , disponibles );
                        for(int k = 0; k<Jugador.size(); k++)
                        {
                            if(EsVictoria( Jugador.get(k), filas , columnas , conecta ))//SI UNO DE LOS HIJOS GENERADOS ES CONDICIÓN DE VICTORIA, NO HAY QUE EXTENDER ESA RAMA DEL ARBOL
                            {
                                poda.add(k);
                            }
                        }
                        support.get(i).get(j).setNodosHijos(Jugador,poda);
                        apoyo.add(support.get(i).get(j).getNodosHijos());
                        apoyo2.add(support.get(i).get(j).getNodosHijosMatriz());
                        for(int l = 0; l<apoyo.size(); l++)
                        {
                            for(int t = 0; t<apoyo.get(l).size(); t++)
                            {
                                heuristica = ValorHeuristico(apoyo2.get(l).get(t),filas,columnas,conecta);
                                apoyo.get(l).get(t).setHeuristica(heuristica);
                            } 
                        }
                        poda.clear();
                    }
                }

//                System.out.println(" --- TURNO JUGADOR --- ");
//                System.out.println(" NIVEL / PROFUNDIDAD " + contador);
//                for(int i = 0; i<apoyo2.size(); i++)
//                {
//                    System.out.println("NODO HIJO Nº " + i);
//                    for(int j = 0; j<apoyo2.get(i).size(); j++)
//                    {
//                        System.out.println(" COMBINACIÓN Nº " + (j + 1) + "DEL NODO HIJO Nº" + (i + 1));
//                        System.out.println("VALOR HEURISTICO: " + ValorHeuristico(apoyo2.get(i).get(j),filas,columnas,conecta));
//                        MuestraTablero( apoyo2.get(i).get(j)  , filas , columnas);
//                    }
//                }

                contador++;
                if(contador==profundidad_limite || contador>profundidad_limite)
                {break;}
                support.clear();
                support_matrix.clear();

            }
        }
        
        return n;
    }
    
    boolean CompruebaHojas( ArrayList<ArrayList<int[][]>> m)
    {
        boolean hoja = true;
        for(int i = 0; i<m.size(); i++)
        {
            if(m.get(i).size()!=1)
            {hoja = false;}
        }
        return hoja;
    }
    
    int Heuristica(int[][] m , int filas , int columnas , int conecta , int turno)
    {
        // Utilizaremos unos flags, para ver si se puede dar el 4 em raya en las
        // distintas direcciones. Si no se puede dat, no lo comprobaremos directamente.
        // Las direcciones a comprobar, serán , izquierda(horizontal) , 
        // derecha(horizontal), diagonal-izquierda , diagonal-derecha y vertical arriba.
        int c;
        int h;//VALOR HEURÍSTICO INDIVIDUAL
        
        ArrayList<Integer> ValorHeuristico = new ArrayList();
        int heuristica = 0;//VALOR DE SALIDA
        
        boolean HorizontalIzquierda;
        boolean HorizontalDerecha;
        boolean DiagonalIzquierda;
        boolean DiagonalDerecha;
        boolean VerticalArriba;
        
        for(int i = 0; i<filas; i++)
        {
            for(int j = 0; j<columnas; j++)
            {
                c=0;
                h=10;
                HorizontalIzquierda = false;
                HorizontalDerecha = false;
                DiagonalIzquierda = false;
                DiagonalDerecha = false;
                VerticalArriba = false;

                if(m[i][j]==turno)
                {
                    //Primero vamos a comprobar las horizontales
                    //VAMOS A MIRAR HACIA LA IZQUIERDA
                    if(j>=conecta-1)
                    {
                        //VAMOS A MIRAR TODAS LAS CASILLAS HACIA LA IZQUIERDA PARA VER SI FUERA POSIBLE HACER 4 EN RAYA EN HORIZONTAL
                        for(int k = j; k>=j-(conecta-1); k--)
                        {
                            if(m[i][k]!=turno && m[i][k]!=0)//ESTO QUIERE DECIR QUE EN MEDIO HAY UNA FICHA DEL RIVAL
                            {
                                HorizontalIzquierda = false;
                            }
                            else
                            {
                                //AQUÍ IRÁ EN UN FUTURO LA HEURÍSTICA
                                c++;
                                if(c==conecta)
                                {
                                    HorizontalIzquierda = true;
                                }
                            }
                            
                        }
                    }
                    c=0;
                    //VAMOS A MIRAR HACIA LA DERECHA
                    if(columnas-j>=conecta-1)
                    {
                        //VAMOS A MIRAR TODAS LAS CASILLAS HAZIA LA IZQUIERDA PARA VER SI FUERA POSIBLE HACER 4 EN RAYA EN HORIZONTAL
                        for(int k = j; k<=conecta; k++)
                        {
                            if(m[i][k]!=turno && m[i][k]!=0)//ESTO QUIERE DECIR QUE EN MEDIO HAY UNA FICHA DEL RIVAL
                            {
                                HorizontalDerecha = false;
                            }
                            else
                            {
                                //AQUÍ IRÁ EN UN FUTURO LA HEURÍSTICA
                                c++;
                                if(c==conecta)
                                {
                                    HorizontalDerecha = true;
                                }
                            }
                        }
                    
                    }
                    c=0;
                    //Ahora vamos a comprobar la vertical hacia arriba
                    //MIRAMOS SI HAY ESPACIO ENCIMA DE NUESTRAS CABEZAS
                    if((i-(filas-1))>=0)
                    {
                        for(int k=i; k>=0; k--)
                        {
                            if(m[k][j]!=turno && m[k][j]!=0)
                            {
                                VerticalArriba = false;
                            }
                            else
                            {
                                //AQUÍ IRÁ EN UN FUTURO LA HEURÍSTICA
                                c++;
                                if(c==conecta)
                                {
                                    VerticalArriba = true;
                                }
                            }
                        }
                    }
                    c=0;
                    //Ahora miraremos ambas diagonales
                    //Las diagonales son la combinación de las condiciones anteriores
                    //sobre las horizontales añadidas a la vertical
                    //MIRAMOS PRIMERO LA DIAGONAL DERECHA
                    if( (columnas-j>=conecta) && ((i-(filas-1))>=0))
                    {
                        //SI HEMOS ENTRADO EN ESTE ESPACIO, SIGNIFICA QUE 
                        //PODEMOS COMPROBAR LA DIAGONAL DERECHA COMPLETA SIN MIEDO
                        //A QUE NOS SALGAMOS DE LA MATRIZ
                        //PODEMOS ASUMIR EL C++ GRACIAS A LA PRIMERA CONDICIÓN DE TODAS
                        c++;
                        if(m[i-1][j+1]==0 || m[i-1][j+1]==turno)
                        {
                            c++;
                        }
                        if(m[i-2][j+2]==0 || m[i-2][j+2]==turno)
                        {
                            c++;
                        }
                        if(m[i-3][j+3]==0 || m[i-3][j+3]==turno)
                        {
                            c++;
                        }
                        //AQUÍ IRÁ EN UN FUTURO LA HEURÍSTICA
                        if(c==conecta)
                        {
                            DiagonalDerecha = true;
                        }
                    }
                    c=0;
                    //MIRAMOS PRIMERO LA DIAGONAL IZQUIERDA
                    if( ((i-(filas-1))>=0) && (j>=conecta-1))
                    {
                        //SI HEMOS ENTRADO EN ESTE ESPACIO, SIGNIFICA QUE 
                        //PODEMOS COMPROBAR LA DIAGONAL DERECHA COMPLETA SIN MIEDO
                        //A QUE NOS SALGAMOS DE LA MATRIZ
                        //PODEMOS ASUMIR EL C++ GRACIAS A LA PRIMERA CONDICIÓN DE TODAS
                        c++;
                        if(m[i-1][j-1]==0 || m[i-1][j-1]==turno)
                        {
                            c++;
                        }
                        if(m[i-2][j-2]==0 || m[i-2][j-2]==turno)
                        {
                            c++;
                        }
                        if(m[i-3][j-3]==0 || m[i-3][j-3]==turno)
                        {
                            c++;
                        }
                        //AQUÍ IRÁ EN UN FUTURO LA HEURÍSTICA
                        if(c==conecta)
                        {
                            DiagonalIzquierda = true;
                        }
                    }
                    c=0;  
                }

                if(HorizontalIzquierda)
                {
                    for(int k = j; k>=j-(conecta-1); k--)
                    {
                        if(m[i][k]!=turno)
                        {
                            break;
                        }
                        else
                        {
                            if(k!=j)
                            {
                                h = h * 10;
                            }
                        }
                    }
                    ValorHeuristico.add(h);
                }
                h = 10;
                if(HorizontalDerecha)
                {
                    for(int k = j; k<conecta; k++)
                    {
                        if(m[i][k]!=turno)
                        {
                            break;
                        }
                        else
                        {
                            if(k!=j)
                            {
                                h = h * 10;
                            }
                        }
                    }
                    ValorHeuristico.add(h);
                }
                h = 10;
                if(VerticalArriba)
                {
                    for(int k=i; k>=0; k--)
                    {
                        if(m[k][j]!=turno)
                        {
                            break;
                        }
                        else
                        {
                            if(k!=i)
                            {
                                h = h * 10;
                            }
                        }
                    }
                    ValorHeuristico.add(h);
                }
                h = 10;
                if(DiagonalIzquierda)
                {
                    c++;
                    if(m[i-1][j-1]==turno)
                    {
                        c++;
                    }
                    if(m[i-2][j-2]==turno)
                    {
                        c++;
                    }
                    if(m[i-3][j-3]==turno)
                    {
                        c++;
                    }
                    h = (int) Math.pow(h, c);
                    ValorHeuristico.add(h);
                    
                }
                c = 0;
                h = 10;
                if(DiagonalDerecha)
                {
                    c++;
                    if(m[i-1][j+1]==turno)
                    {
                        c++;
                    }
                    if(m[i-2][j+2]==turno)
                    {
                        c++;
                    }
                    if(m[i-3][j+3]==turno)
                    {
                        c++;
                    }
                    h = (int) Math.pow(h, c);
                    ValorHeuristico.add(h);
                }
                c = 0;
                h = 10;
 
            }
        }
        
        for(int i = 0; i<ValorHeuristico.size(); i++)
        {
            heuristica = heuristica + ValorHeuristico.get(i);
        }
        
        return heuristica;
    }
    
    int ValorHeuristico(int[][] m , int filas , int columnas , int conecta)
    {
        int n1 = Heuristica(m , filas , columnas , conecta , 1);
        int n2 = Heuristica(m , filas , columnas , conecta ,-1);
        
        int resultado = n2 - n1;
        
        return resultado;
    }
    
    int Diferencia(int[][] m , int[][] n , int filas , int columnas)
    {
        for(int i = 0; i<filas; i++)
        {
            for(int j = 0; j<columnas; j++)
            {
                if(m[i][j]!=n[i][j])
                {
                    System.out.println("Decision: " + j);
                    return j;
                }
            }
        }
        
        return 1;
    }
    
    int minYmax( Nodo n )
    {
        //TURNO IA -> MAX
        ArrayList<Nodo> nivel_uno = n.getNodosHijos();
        
        //TURNO JG -> MIN
        ArrayList<ArrayList<Nodo>> nivel_dos = new ArrayList();
        for(int i = 0; i<nivel_uno.size(); i++)
        {
            nivel_dos.add(nivel_uno.get(i).getNodosHijos());
        }
        //TURNO IA -> MAX
        ArrayList<ArrayList<Nodo>> nivel_tres = new ArrayList();
        for(int i = 0; i<nivel_dos.size(); i++)
        {
            for(int j = 0; j<nivel_dos.get(i).size(); j++)
            {
                nivel_tres.add(nivel_dos.get(i).get(j).getNodosHijos());
            }
        }
        //TURNO JG -> MIN
        ArrayList<ArrayList<Nodo>> nivel_cuatro = new ArrayList();
        for(int i = 0; i<nivel_tres.size(); i++)
        {
            for(int j = 0; j<nivel_tres.get(i).size(); j++)
            {
                nivel_cuatro.add(nivel_tres.get(i).get(j).getNodosHijos());
            }
        }
        //COMENZAMOS EL ALGORITMO MINIMAX
        //COMO ES UN MAX, NOS QUEDAREMOS SOLO CON LOS MAS GRANDES DE CADA NODO
        //AQUI PARECE QEU ESTÁ EL PROBLEMA
        ArrayList<Integer> soporte = new ArrayList();
        ArrayList<Integer> max = new ArrayList();
        int menor = 999999;
        for(int i = 0; i<nivel_cuatro.size(); i++)
        {
            for(int j = 0; j<nivel_cuatro.get(i).size(); j++)
            {
                soporte.add(nivel_cuatro.get(i).get(j).getHeuristica());
            }
            for(int j = 0; j<soporte.size(); j++)
            {
                if(soporte.get(j)<menor)
                {
                    menor=soporte.get(j);
                }
            }
            max.add(menor);
            soporte.clear();
            menor = 999999;
        }
        //A CONTINUACIÓN, SERÍA UN MIN, POR LO QUE SE SACA EL MINIMO
        int mayor = -999999;
        ArrayList<Integer> min = new ArrayList();
        int contador = 0;
        for(int i = 0; i<nivel_tres.size(); i++)
        {
            for(int j = 0; j<nivel_tres.get(i).size(); j++)
            {
                if(max.get(contador)>mayor)
                {mayor=max.get(contador);}
                contador++;
            }
            min.add(mayor);
            mayor = -999999;
        }
        //AHORA VUELVE A TOCAR MAX
        max.clear();
        menor=999999;
        contador=0;
        for(int i = 0; i<nivel_dos.size(); i++)
        {
            for(int j = 0; j<nivel_dos.get(i).size(); j++)
            {
                if(min.get(contador)<menor)
                {menor=min.get(contador);}
                contador++;
            }
            max.add(menor);
            menor = 999999;
        }
        //Y TERMINAMOS ELIGIENDO MIN
        mayor=-999999;
        int pos = 0;
        //CUANDO EL ALGORITMO LLEGA HASTA AQUI, TODOS LOS ELEMENTOS DEL VECTOR
        //VALEN LO MISMO, XQ ???
        for(int i = 0; i<nivel_uno.size(); i++)
        {
            System.out.println(max.get(i));
            if(max.get(i)>mayor)
            {
                mayor=max.get(i);
                pos = i;
            }
        }
        //ESTE ES EL VALOR ELEGIDO DEFINITIVAMENTE
        System.out.println("Valor seleccionado finalmente: ");
        System.out.println(menor);
        
        //EN NUESTRO CASO APROVECHAMOS EL ULTIMO BUCLE DE NUESTRO ALGORITMO
        //PARA DEVOLVER LA POSICION DEL NODO SELECCIONADO EN EL PRIMER 
        //ARRAY DE HIJOS, FACILITANDO EL CÓDIGO
        
        int retorno = pos;
        System.out.println("Posición del vector devuelta:" + retorno);
        return retorno;
    }
    
    
    @Override
    public int turnoJugada(Grid tablero, int conecta) 
    {

        int[][] m = tablero.toArray();
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        int disponibles = ColumnasDisponibles( m , filas , columnas );
        ArrayList<int[][]> hijos = DevuelveHijosIA( m , filas , columnas , disponibles );
        
        //GENERAMOS EL ARBOL
        Nodo n = GenerarArbol( tablero , 4);
        //OBTENEMOS LA DECISION
        int mym = minYmax( n );

        int[][] decision = hijos.get(mym);
        
        //CON LA SIGUIENTE FUNCIÓN, OBTENDREMOS UN ENTERO CON LA COLUMNA DE LA MATRIZ
        //QUE ES DISTINTA, PUDIENDO ASI TOMAR LA DECISIÓN
        int columna_decisiva = Diferencia(m , decision , filas ,columnas);
        
        System.out.println("Mostrando situación actual:");
        return tablero.checkWin(tablero.setButton(columna_decisiva, Conecta4.PLAYER2), columna_decisiva, conecta);

    } // turnoJugada

} // IAPlayer
