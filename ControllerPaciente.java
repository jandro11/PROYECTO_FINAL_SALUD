package Controller;

import Config.Conexion;
import Entidad.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerPaciente {
    Conexion con = new Conexion();
    JdbcTemplate jdbctemplate = new JdbcTemplate(con.Conectar());
    ModelAndView mav = new ModelAndView();    
    @RequestMapping("/Paciente/listar.htm")
    public ModelAndView Listar(){
        String sql = "Select ID, Nombre, Direccion, Telefono, Correo from tbpaciente";
        List datos=jdbctemplate.queryForList(sql);
        mav.addObject("lista",datos);
        mav.setViewName("/Paciente/listar");
        return mav;
    }
    
    @RequestMapping(value="/Paciente/agregar.htm", method = RequestMethod.GET )
    public ModelAndView Agregar(){
        mav.addObject(new Paciente());
        mav.setViewName("/Paciente/agregar");
        return mav;
    }
    
    @RequestMapping(value="/Paciente/agregar.htm", method = RequestMethod.POST )
    public ModelAndView Agregar(Paciente objEsp){
        String sql = "insert into tbpaciente  (ID, Correo, Direccion, Nombre, Telefono )values(?,?,?,?,?)";
        this.jdbctemplate.update(sql,objEsp.getID(), objEsp.getCorreo(), 
                    objEsp.getDireccion(),objEsp.getNombre(), objEsp.getTelefono());        
        return new ModelAndView("redirect:/Paciente/listar.htm");
    }
    
    @RequestMapping(value="/Paciente/editar.htm", method = RequestMethod.GET )
    public ModelAndView Editar(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("ID"));
        String sql = "Select * from tbpaciente where ID = ?";
        List datos=jdbctemplate.queryForList(sql,id);
        mav.addObject("lista",datos);
        mav.setViewName("/Paciente/editar");
        return mav;
    }
    
    @RequestMapping(value="/Paciente/editar.htm", method = RequestMethod.POST )
    public ModelAndView Editar(Paciente objEsp){
        String sql = "update tbpaciente set Correo=?, Direccion=?, Nombre=?, Telefono=? where ID=?";
        this.jdbctemplate.update(sql, objEsp.getCorreo(),objEsp.getDireccion(), 
                objEsp.getNombre(), objEsp.getTelefono(), objEsp.getID());        
        return new ModelAndView("redirect:/Paciente/listar.htm");
    }
    
    @RequestMapping(value="/Paciente/eliminar.htm", method = RequestMethod.GET )
    public ModelAndView Eliminar(HttpServletRequest request){
         int id = Integer.parseInt(request.getParameter("ID"));
        String sql = "Select * from tbpaciente where ID = ?";
        List datos=jdbctemplate.queryForList(sql,id);
        mav.addObject("lista",datos);
        mav.setViewName("/Paciente/eliminar");
        return mav;
    }
    
    @RequestMapping(value="/Paciente/eliminar.htm", method = RequestMethod.POST )
    public ModelAndView Eliminar(Paciente objEsp){
        String sql = "delete from tbpaciente where ID=?";
        this.jdbctemplate.update(sql, objEsp.getID());        
        return new ModelAndView("redirect:/Paciente/listar.htm");
    }  
    public List ListarPaciente(){
        String sql = "Select * from tbpaciente";
        List datos=jdbctemplate.queryForList(sql);
        return datos;
    } 
}
