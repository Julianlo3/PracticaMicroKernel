package co.unicauca.microkernel.gestionproyectos.app;

import co.edu.unicauca.microkernel.entities.User;
import co.edu.unicauca.microkernel.interfaces.IProjectRepositoryPlugin;
import co.edu.unicauca.microkernel.microkernel_plugin.Plugin_ArrayList;
import co.unicauca.microkernel.gestionproyectos.access.ProjectsRepositoryArrayPlugin;
import co.unicauca.microkernel.gestionproyectos.core.domain.services.ProjectService;
import co.unicauca.microkernel.gestionproyectos.core.plugin.manager.PluginManager;
import co.unicauca.microkernel.gestionproyectos.presentation.Home;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal de la aplicación que gestiona proyectos. Se encarga de
 * inicializar el repositorio de proyectos, registrar usuarios y administrar la
 * asignación de proyectos.
 *
 * @author libardo
 */
public class Main {

    /**
     * Obtiene la ruta base donde está corriendo la aplicación, sin importar que
     * sea desde un archivo .class o desde un paquete .jar.
     *
     */
    private static String getBaseFilePath() {
        try {
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = URLDecoder.decode(path, "UTF-8"); //This should solve the problem with spaces and special characters.
            File pathFile = new File(path);
            if (pathFile.isFile()) {
                path = pathFile.getParent();

                if (!path.endsWith(File.separator)) {
                    path += File.separator;
                }

            }

            return path;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error al eliminar espacios en la ruta del archivo", ex);
            return null;
        }
    }

    // epa
    /**
     * Método principal que ejecuta la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {

        //Inicializar el plugin manager con la ruta base de la aplicación.
        String basePath = getBaseFilePath();
        try {
            PluginManager.init(basePath);

        } catch (Exception ex) {
            Logger.getLogger("Application").log(Level.SEVERE, "Error al ejecutar la aplicación", ex);
        }

        // Inicialización del repositorio de proyectos (pendiente crear la fábrica)
        //IProjectRepositoryPlugin repositorioProyectos = new ProjectsRepositoryArrayPlugin();
        IProjectRepositoryPlugin repoPlugin = new Plugin_ArrayList();
        IProjectRepositoryPlugin repoLocal = new ProjectsRepositoryArrayPlugin();

        // Registrar el plugin en el sistema
        PluginManager.registerPlugin(repoPlugin);
        PluginManager.registerPlugin(repoLocal);

        List<IProjectRepositoryPlugin> listaPlugins = PluginManager.getPlugins();

        System.out.println("Lista de plugins:");
        for (IProjectRepositoryPlugin plugin : listaPlugins) {
            System.out.println("- " + plugin.getClass().toString());
        }

        // --------------- local -----------------------------
        // Crear instancia del servicio de proyecto local
        System.out.println("Forma local con ProjectsRepositoryArrayPlugin");
        ProjectService projectServiceLocal = new ProjectService(repoLocal);
       
        // Crear usuarios
        User empresa = new User("TechCorp", "contacto@techcorp.com", "EMPRESA");
        User estudiante = new User("Juan Pérez", "juan@example.com", "ESTUDIANTE");

        // Registrar un nuevo proyecto
        projectServiceLocal.registerProject("Sistema de Inventarios", "Desarrollar un sistema de gestión de inventarios.", empresa);

        // Listar proyectos disponibles
        System.out.println("\nProyectos disponibles:");
        projectServiceLocal.listProject();

        // Asignar un estudiante al proyecto
        projectServiceLocal.assignProject("Sistema de Inventarios", estudiante);

        // Listar proyectos nuevamente para reflejar los cambios
        System.out.println("\nProyectos después de la asignación:");
        projectServiceLocal.listProject();

        // Listar usuarios 
        System.out.println("Usuarios creados");
        for (User user : User.getUser()) {
            System.out.println("Nombre: " + user.getName() + ", Email: " + user.getEmail() + ", Rol: " + user.getRole());
        }

        // ---------------------------------------------------
        
        // ------------- plugin -----------------------
         
          // Crear instancia del servicio de proyecto plugin
          System.out.println(" Forma plugin con  Plugin_ArrayList");
        ProjectService projectServicePlugin = new ProjectService(repoPlugin);
       
        // Crear usuarios
        User empresa1 = new User("TechCorp plugin", "contacto@techcorp.com", "EMPRESA");
        User estudiante1 = new User("Juan Pérez plugin", "juan@example.com", "ESTUDIANTE");

        // Registrar un nuevo proyecto
        projectServicePlugin.registerProject("Sistema de Inventarios", "Desarrollar un sistema de gestión de inventarios.", empresa1);

        // Listar proyectos disponibles
        System.out.println("\nProyectos disponibles:");
        projectServicePlugin.listProject();

        // Asignar un estudiante al proyecto
        projectServicePlugin.assignProject("Sistema de Inventarios", estudiante1);

        // Listar proyectos nuevamente para reflejar los cambios
        System.out.println("\nProyectos después de la asignación:");
        projectServicePlugin.listProject();

        // Listar usuarios 
        System.out.println("Usuarios creados");
        for (User user : User.getUser()) {
            System.out.println("Nombre: " + user.getName() + ", Email: " + user.getEmail() + ", Rol: " + user.getRole());
        }
        
        //-----------------------------------------------------
        
        Home home = new Home(listaPlugins);
        home.setVisible(true);
    }
}
