package tw.com.ctt.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBlackWhiteDao;
import tw.com.ctt.model.BlackWhiteBean;
import tw.com.ctt.service.IBlackWhiteService;

/**
 * <p>
 * created date:2018/06/05 11:00<br>
 * modify date:2018/06/05 11:00<br>
 * BlackWhiteServiceImpl for manage black white list
 * 
 * @author paul <br>
 * @version 0.0.1 <br>
 */
public class BlackWhiteServiceImpl extends BaseService implements IBlackWhiteService {

	private static final long serialVersionUID = -8372202095044533661L;
	private static final Logger LOG = LogManager.getLogger(BlackWhiteServiceImpl.class.getName());

	/**
	 * initial BlackWhiteServiceImpl()
	 */
	public BlackWhiteServiceImpl() {
		super();
		LOG.debug("BlackWhiteServiceImpl() start...");
	}

	/**
	 * initial BlackWhiteServiceImpl(userId, userIp)
	 * 
	 * @param userId
	 *            is login user
	 * @param userIp
	 *            is user's ip addr
	 */
	public BlackWhiteServiceImpl(long userId, String userIp) {
		super(userId, userIp);
		LOG.debug("BlackWhiteServiceImpl(" + userId + "," + userIp + ") start...");
	}

	/**
	 * get all black white list
	 */
	@Override
	public List<Object> getAllList() {
		return ((IBlackWhiteDao) dao).getAllList();
	}

	/**
	 * get black white list by (name or ip)
	 */
	@Override
	public List<Object> getList(String name, String ip) {
		return ((IBlackWhiteDao) dao).getList(name, ip);
	}

	/**
	 * insert a row into ctt_manager.ctt_black_white_list
	 */
	@Override
	public int insertRow(BlackWhiteBean bean) {
		return ((IBlackWhiteDao) dao).insertRow(bean);
	}

	/**
	 * update a row from ctt_manager.ctt_black_white_list
	 */
	@Override
	public int updateRow(BlackWhiteBean bean) {
		return ((IBlackWhiteDao) dao).updateRow(bean);
	}

	/**
	 * delete a row from ctt_manager.ctt_black_white_list
	 */
	@Override
	public int deleteRow(int id) {
		return ((IBlackWhiteDao) dao).deleteRow(id);
	}
}
