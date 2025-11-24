package biblioteca;

public class MainBiblioteca {
	
    public static void main(String[] args) {
        InicioSesion dialog = new InicioSesion();
        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.isAutenticado()) {
            System.out.println("Login correcto, abrir ventana principal"); //De prueba, para cambiarlo posteriormente
            // new VentanaPrincipal().setVisible(true);
        } else {
            System.exit(0);
        }
    }

}
