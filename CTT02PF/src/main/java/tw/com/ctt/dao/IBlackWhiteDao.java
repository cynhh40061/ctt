package tw.com.ctt.dao;

import java.util.List;

import tw.com.ctt.model.BlackWhiteBean;

public interface IBlackWhiteDao {

	public List<Object> getAllList();

	public List<Object> getList(String name, String ip);

	public int insertRow(BlackWhiteBean bean);

	public int updateRow(BlackWhiteBean bean);

	public int deleteRow(int id);

}
