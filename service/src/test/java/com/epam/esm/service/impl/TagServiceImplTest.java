package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    @Mock
    TagDao tagDao;
    @InjectMocks
    TagServiceImpl service;

    List<Tag> tags;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tags = Arrays.asList(
                new Tag(1, "tag1"),
                new Tag(2, "tag2"),
                new Tag(3, "tag3"),
                new Tag(4, "tag4"),
                new Tag(5, "tag5")
        );
    }

    @Test
    void find() {
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
    void delete() {
        long id = 1;
        when(tagDao.remove(isA(Long.class))).thenReturn(1);
        service.delete(id);
        verify(tagDao).remove(anyLong());
    }
}