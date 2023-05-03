package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Category;
import com.ax.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className: CategoryController
 * description: 分类管理
 *
 * @author: axiang
 * date: 2023/5/1 0001 18:07
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * description: 分类管理
     * @param category: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("分类管理...");
        return categoryService.insert(category);
    }

    /**
     * description: 分页查分类信息
     * @param page:
     * @param pageSize:
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/page")
    public R<Page> getCategoryByPage(int page, int pageSize) {
        log.info("分页查分类信息...");
        return categoryService.getCategoryByPage(page, pageSize);
    }

    /**
     * description: 删除分类
     * @param ids: 分类id
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类...");
        return categoryService.deleteCategory(ids);
    }

    /**
     * description: 修改分类信息
     * @param category: 封装修改数据
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类...");
        return categoryService.updateCategory(category);
    }

    /**
     * description: 用于菜品管理新建菜品选择菜品分类下拉框
     * @param category: 封装type
     * @return: R<List < Category>> <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/list")
    public R<List<Category>> listCategory(Category category) {
        return categoryService.listCategory(category);
    }
}
