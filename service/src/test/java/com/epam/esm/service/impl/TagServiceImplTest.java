package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDao tagDao;
    @InjectMocks
    private TagServiceImpl service;

    private List<Tag> tags =  Arrays.asList(
            new Tag(1, "tag1"),
            new Tag(2, "tag2"),
            new Tag(3, "tag3"),
            new Tag(4, "tag4"),
            new Tag(5, "tag5")
    );

    @BeforeEach
    public void setUp() {
        service=new TagServiceImpl(tagDao);
    }

    @AfterEach
    public void checkMocks(){
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    void find() throws ServiceException {
        Tag tag = new Tag(1, "tag");
        when(tagDao.find(1)).thenReturn(Optional.of(tag));
        Optional<Tag> result = service.find(1);
        assertTrue(result.isPresent());
        verify(tagDao).find(1);
    }

    @Test
    void findAll() {
        when(tagDao.findAll()).thenReturn(List.copyOf(tags));
        List<Tag> result = service.findAll();
        assertTrue(!result.isEmpty());
        verify(tagDao).findAll();
    }

    @Test
    void delete() throws ServiceException {
        long id = 1;
        when(tagDao.remove(isA(Long.class))).thenReturn(1);
        service.delete(id);
        verify(tagDao).remove(anyLong());
    }
}