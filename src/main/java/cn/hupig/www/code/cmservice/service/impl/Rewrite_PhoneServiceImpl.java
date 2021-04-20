package cn.hupig.www.code.cmservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.Phone;
import cn.hupig.www.code.cmservice.repository.PhoneRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_PhoneService;
import cn.hupig.www.code.cmservice.service.dto.PhoneDTO;
import cn.hupig.www.code.cmservice.service.mapper.PhoneMapper;
import cn.hupig.www.code.cmservice.service.utils.Numbers;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.PhoneAlreadyUsedException;

/**
 * Service Implementation for managing {@link Phone}.
 */
@Service
@Transactional
public class Rewrite_PhoneServiceImpl implements Rewrite_PhoneService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_PhoneServiceImpl.class);

    private final PhoneRepository phoneRepository;

    private final PhoneMapper phoneMapper;

    public Rewrite_PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    @Override
    public void sendCode(String phoneNumber) {
        log.debug("Request to send Phone : {}", phoneNumber);
        if (judgePhone(phoneNumber)) {
        	throw new PhoneAlreadyUsedException(0);
        }
        Phone phone = new Phone();
        phone.setPhone(phoneNumber);
        phone.setEffectiveTime(Times.getHourInstant(24));
        phone.setSendTime(Times.getInstant());
        phone.setCreateUser(phoneNumber);
        phone.setCreatTime(Times.getInstant());
        phone.setUpdateUser(phoneNumber);
        phone.setUpdateTime(Times.getInstant());
        phone.setCode(Integer.valueOf(Numbers.getRandom(6)));
        try {
//			SendSms.sendCode(phoneNumber, phone.getCode().toString()); // 发信息
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			phone = phoneRepository.save(phone); // 存入数据库
		}
    }
    
    private boolean judgePhone(String phone) {
    	return phoneRepository.findFirstByPhoneAndEffectiveTimeAfter(
				phone, Times.getInstant()).isPresent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Phones");
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"sendTime");
        return phoneRepository.findAll(newPageable)
            .map(phoneMapper::toDto);
    }
    
}
