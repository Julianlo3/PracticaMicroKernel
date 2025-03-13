/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.microkernel.interfaces;

import co.edu.unicauca.microkernel.entities.Project;
import java.util.List;

/**
 *
 * @author lopez
 */
    /**
 * Interfaz que define el contrato para la gestión de proyectos en un repositorio.
 * Permite agregar, buscar y listar proyectos.
 * 
 * Implementaciones de esta interfaz pueden proporcionar diferentes estrategias de almacenamiento,
 * como bases de datos, archivos o servicios web.
 * 
 * @author C2T26Q3
 */
public interface IProjectRepositoryPlugin extends IPlugin{

    /**
     * Agrega un nuevo proyecto al repositorio.
     * 
     * @param proyecto Objeto de tipo Project que representa el proyecto a agregar.
     */
    void addProject(Project proyecto);

    /**
     * Busca un proyecto en el repositorio por su título.
     * 
     * @param titulo Título del proyecto a buscar.
     * @return El objeto Project si se encuentra, de lo contrario, null.
     */
    Project findProjectByTitle(String titulo);

    /**
     * Obtiene la lista de todos los proyectos almacenados en el repositorio.
     * 
     * @return Lista de objetos Project.
     */
    List<Project> getProjects();
}
