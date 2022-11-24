package com.company;

public class Libros implements Runnable{

    int id;
    static boolean[] libros=new boolean[9];
    public Libros(int id){
        this.id=id;
    }
    @Override
    public void run() {

        int libro1= reservarLibro();
        int libro2=reservarLibro();
        while(libro2 == libro1) {
            libro2 = reservarLibro();
        }
        try {
        synchronized (libros){

            while(libros[libro1]||libros[libro2]){//Mientras q alguno de los libros este ocupada, esperaremos
                libros.wait();
            }
            libros[libro1]=true;
            libros[libro2]=true;

        }
            System.out.println("El estudiante "+id+" cogió los libros "+libro1+" y "+libro2);
            Thread.sleep((int)(Math.random()*3000)+2000);
            System.out.println("El estudiante "+id+" dejo libres los libros "+libro1+" y "+libro2);
            libros.notifyAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devolvera un numero aleatorio entre 0 y 8
     * @return
     */
    private int reservarLibro(){
        return (int)(Math.random()*9);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(new Libros(i+1)).start();
        }
    }

}
