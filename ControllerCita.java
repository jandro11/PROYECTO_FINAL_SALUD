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
public class ControllerCita {
    Conexion con = new Conexion();
    JdbcTemplate jdbctemplate = new JdbcTemplate(con.Conectar());
    ModelAndView mav = new ModelAndView();    
    @RequestMapping("/Cita/listar.htm")
    public ModelAndView Listar(){
        String sql ="SELECT c.IdCita, p.Nombre AS 'NPaciente',e.Nombre  AS 'NEspecialidad'," +
                    "	d.Nombre  AS 'NDoctor',	h.Nombre AS 'NHorario', c.Fecha, c.Diagnostico," +
                    "	c.Tratamiento, c.Observaciones FROM tbCita c" +
                    "	INNER JOIN tbpaciente p" +
                    "		ON c.IdPaciente = p.ID " +
                    "	INNER JOIN tbdoctor d" +
                    "		ON c.IdDoctor = d.IdDoctor " +
                    "	INNER JOIN tbespecialidad e" +
                    "		ON d.IdEspecialidad =  e.ID" +
                    "	INNER JOIN tbhorario h" +
                    "		ON d.idHorario = h.IDHorario" ;
        List datos=jdbctemplate.queryForList(sql);
        mav.addObject("lista",datos);
        mav.setViewName("/Cita/listar");
        return mav;
    }
    
    @RequestMapping(value="/Cita/agregar.htm", method = RequestMethod.GET )
    public ModelAndView Agregar(){
        mav.addObject("listaPaciente",new ControllerPaciente().ListarPaciente());
        mav.addObject("listaEspecialidad",new ControllerEspecialidad().ListarEspecialidad());
        mav.addObject("listaDoctor",new ControllerDoctor().ListarDoctor());
        mav.addObject("listaHorario",new ControllerHorario().ListarHorario());
            mav.setViewName("/Cita/agregar");
        return mav;
    }
    
    @RequestMapping(value="/Cita/agregar.htm", method = RequestMethod.POST )
    public ModelAndView Agregar(Cita objEsp){
        String sql = "insert into tbcita (IdCita, IdPaciente, IdEspecialidad, IdDoctor, IdHorario, Fecha, Diagnostico, Tratamiento, Observaciones )values(?,?,?,?,?,?,?,?,?)";
        this.jdbctemplate.update(sql,objEsp.getIdCita(), objEsp.getIdPaciente(), 
                    objEsp.getIdEspecialidad(),objEsp.getIdDoctor(), objEsp.getIDHorario(),  
                    objEsp.getFecha(), objEsp.getDiagnostico(),objEsp.getTratamiento(), objEsp.getObservaciones());        
        return new ModelAndView("redirect:/Cita/listar.htm");
    }
    
    @RequestMapping(value="/Cita/editar.htm", method = RequestMethod.GET )
    public ModelAndView Editar(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("ID"));
        String sql = "Select * from tbcita where IdCita = ?";
        List datos=jdbctemplate.queryForList(sql,id);
        mav.addObject("lista",datos);
        mav.addObject("listaPaciente",new ControllerPaciente().ListarPaciente());
        mav.addObject("listaEspecialidad",new ControllerEspecialidad().ListarEspecialidad());
        mav.addObject("listaDoctor",new ControllerDoctor().ListarDoctor());
        mav.addObject("listaHorario",new ControllerHorario().ListarHorario());
        mav.setViewName("/Cita/editar");
        return mav;
    }
    
    @RequestMapping(value="/Cita/editar.htm", method = RequestMethod.POST )
    public ModelAndView Editar(Cita objEsp){
        String sql = "update tbCita set IdPaciente=?, IdEspecialidad=?, "
                + "IdDoctor=?, IDHorario=?, Fecha=?, Diagnostico=?, Tratamiento=? "
                + "Observacioes=? where IdCita=?";
        this.jdbctemplate.update(sql, objEsp.getIdPaciente(), objEsp.getIdEspecialidad(), objEsp.getIdDoctor(),
                objEsp.getIDHorario(), objEsp.getFecha(), objEsp.getDiagnostico(),objEsp.getTratamiento(), objEsp.getObservaciones());        
        return new ModelAndView("redirect:/Cita/listar.htm");
    }
    
    @RequestMapping(value="/Cita/eliminar.htm", method = RequestMethod.GET )
    public ModelAndView Eliminar(HttpServletRequest request){
         int id = Integer.parseInt(request.getParameter("ID"));
        String sql = "Select * from tbcita where IdCita = ?";
        List datos=jdbctemplate.queryForList(sql,id);
        mav.addObject("lista",datos);
        mav.setViewName("/Cita/eliminar");
        return mav;
    }
    
    @RequestMapping(value="/Cita/eliminar.htm", method = RequestMethod.POST )
    public ModelAndView Eliminar(Cita objEsp){
        String sql = "delete from tbCita where IdCita=?";
        this.jdbctemplate.update(sql, objEsp.getIdCita());        
        return new ModelAndView("redirect:/Cita/listar.htm");
    }
    
  
}
