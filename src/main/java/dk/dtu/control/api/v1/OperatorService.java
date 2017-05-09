package main.java.dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.dk.dtu.model.IDAL;
import main.java.dk.dtu.model.IDAL.DALException;
import main.java.dk.dtu.model.OperatorDAO;
import main.java.dk.dtu.model.Validation;
import main.java.dk.dtu.model.DTO.OperatorDTO;


@Path("v1/operator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperatorService {
	
	private IDAL dao = new OperatorDAO();

	@GET
	@Path("/{id}")
	public OperatorDTO getOperator(@PathParam("id") String oprID) throws DALException {
		if (Validation.isPositiveInteger(oprID)) {
			return dao.getOperator(Integer.parseInt(oprID));
		} else {
			return null;
		}
	}
	
	@GET
	public List<OperatorDTO> getOperatorList() throws DALException {
		return dao.getOperatorList();
	}

	@POST
	public boolean createOperator(OperatorDTO opr) throws DALException {
		return dao.createOperator(opr);
	}
	
	@PUT
	public boolean updateOperator(OperatorDTO opr) throws DALException {
		return dao.updateOperator(opr);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean deleteOperator(@PathParam("id") String oprID) throws DALException {
		if (Validation.isPositiveInteger(oprID)) {
			return dao.deleteOperator(Integer.parseInt(oprID));
		} else {
			return false;
		}
	}
}
