package tw.com.ctt.service;

import java.util.List;

import tw.com.ctt.model.BlackWhiteBean;

public interface IBlackWhiteService {

	public List<Object> getAllList();

	public List<Object> getList(String name, String ip);

	public int insertRow(BlackWhiteBean bean);

	public int updateRow(BlackWhiteBean bean);

	public int deleteRow(int id);

}
