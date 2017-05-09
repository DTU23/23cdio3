package main.java.dk.dtu.model;

import java.util.ArrayList;

import main.java.dk.dtu.model.DTO.OperatorDTO;

public class OperatorDAO implements IDAL{

	private static ArrayList<OperatorDTO> operatorList = new ArrayList<OperatorDTO>();
	static {
		operatorList.add(new OperatorDTO(1, "Viktor Poulsen", "VP", "1234567890", "test", true, "operator"));
		operatorList.add(new OperatorDTO(2, "Frederik", "FV", "1234567890", "test", false, "foreman"));
		operatorList.add(new OperatorDTO(3, "Christian Niemann", "CN", "1234567890", "test", true, "pharmacist"));
		operatorList.add(new OperatorDTO(4, "Mads Pedersen", "MP", "1234567890", "test", false, "operator"));
	}

	@Override
	public OperatorDTO getOperator(int oprId) throws DALException{
		for (OperatorDTO operator : operatorList) {
			if (operator.getOprId() == oprId) {
				return operator;
			}
		}
		throw new DALException("Operator doesn't exist");
	}

	@Override
	public ArrayList<OperatorDTO> getOperatorList() throws DALException {
		if(!operatorList.isEmpty())
			return operatorList;
		else { throw new DALException("There are no users in the system"); }
	}

	@Override
	public boolean createOperator(OperatorDTO opr) throws DALException {
		try {
			getOperator(opr.getOprId());
			throw new DALException("OperatorID already used");
		} catch (DALException e) {
			return operatorList.add(opr);
		}
	}

	@Override
	public boolean updateOperator(OperatorDTO opr) throws DALException {
		operatorList.remove(getOperator(opr.getOprId()));
		return operatorList.add(opr);
	}

	@Override
	public boolean deleteOperator(int oprId) throws DALException {
		return operatorList.remove(getOperator(oprId));
	}
}