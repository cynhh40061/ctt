package tw.com.ctt.service.impl;

import java.util.List;
import java.util.Map;

import tw.com.ctt.dao.impl.PortionAuthDaoImpl;
import tw.com.ctt.service.IPortionAuthService;

public class PortionAuthServiceImpl extends BaseService implements IPortionAuthService {

	private static final long serialVersionUID = 298748406431290573L;

	public PortionAuthServiceImpl() {

	}

	public PortionAuthServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getLevel3Auth(String url) {
		return ((PortionAuthDaoImpl) dao).getLevel3Auth(url);
	}
}
