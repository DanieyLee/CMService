package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.ArticlEnclosureService;
import cn.hupig.www.code.cmservice.domain.ArticlEnclosure;
import cn.hupig.www.code.cmservice.repository.ArticlEnclosureRepository;
import cn.hupig.www.code.cmservice.service.dto.ArticlEnclosureDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticlEnclosureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArticlEnclosure}.
 */
@Service
@Transactional
public class ArticlEnclosureServiceImpl implements ArticlEnclosureService {

    private final Logger log = LoggerFactory.getLogger(ArticlEnclosureServiceImpl.class);

    private final ArticlEnclosureRepository articlEnclosureRepository;

    private final ArticlEnclosureMapper articlEnclosureMapper;

    public ArticlEnclosureServiceImpl(ArticlEnclosureRepository articlEnclosureRepository, ArticlEnclosureMapper articlEnclosureMapper) {
        this.articlEnclosureRepository = articlEnclosureRepository;
        this.articlEnclosureMapper = articlEnclosureMapper;
    }

    @Override
    public ArticlEnclosureDTO save(ArticlEnclosureDTO articlEnclosureDTO) {
        log.debug("Request to save ArticlEnclosure : {}", articlEnclosureDTO);
        ArticlEnclosure articlEnclosure = articlEnclosureMapper.toEntity(articlEnclosureDTO);
        articlEnclosure = articlEnclosureRepository.save(articlEnclosure);
        return articlEnclosureMapper.toDto(articlEnclosure);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticlEnclosureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticlEnclosures");
        return articlEnclosureRepository.findAll(pageable)
            .map(articlEnclosureMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticlEnclosureDTO> findOne(Long id) {
        log.debug("Request to get ArticlEnclosure : {}", id);
        return articlEnclosureRepository.findById(id)
            .map(articlEnclosureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticlEnclosure : {}", id);
        articlEnclosureRepository.deleteById(id);
    }
}
