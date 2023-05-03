package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * description: 分类管理
     * @param category: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> insert(Category category);

    /**
     * description: 分页查分类信息
     * @param page:
     * @param pageSize:
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<Page> getCategoryByPage(int page, int pageSize);

    /**
     * description: 删除分类
     * @param id: 分类id
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> deleteCategory(Long id);

    /**
     * description: 修改分类信息
     * @param category: 封装数据
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> updateCategory(Category category);

    /**
     * description: 用于菜品管理新建菜品选择菜品分类下拉框
     * @param category: 封装type
     * @return: R<List < Category>> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<List<Category>> listCategory(Category category);

}
