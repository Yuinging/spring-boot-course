package top.yyyin.boot.exception.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.yyyin.boot.exception.VO.BookAccountVO;
import top.yyyin.boot.exception.common.ApiResponse;
import top.yyyin.boot.exception.dto.BookCreateDTO;
import top.yyyin.boot.exception.dto.BookPageQuery;
import top.yyyin.boot.exception.dto.StockAdjustDTO;
import top.yyyin.boot.exception.entity.BookAccount;
import top.yyyin.boot.exception.service.BookAccountService;
import top.yyyin.boot.exception.util.ConvertUtil;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class BookAccountController {
    private final BookAccountService bookAccountService;


    @PostMapping
    public ApiResponse <BookAccountVO> addBook(@Valid @RequestBody BookCreateDTO dto) {
        BookAccount entity = ConvertUtil.convert(dto, BookAccount.class);

        boolean saved = bookAccountService.save(entity);
        if (!saved){
            return ApiResponse.error("添加图书失败");
        }

        BookAccountVO vo = ConvertUtil.convert(entity, BookAccountVO.class);
        return ApiResponse.success("添加图书成功", vo);
    }

    @PutMapping("/{id}")
    public ApiResponse<BookAccountVO> updateBook(@PathVariable @NotNull Long id,
                                                 @Valid @RequestBody BookCreateDTO dto) {
        BookAccount old = bookAccountService.getById(id);
        if (old == null) return ApiResponse.error("图书不存在");

        BookAccount entity = ConvertUtil.convert(dto, BookAccount.class);
        entity.setId(id);
        boolean updated = bookAccountService.updateById(entity);
        if (!updated) return ApiResponse.error("更新失败");

        BookAccountVO vo = ConvertUtil.convert(entity, BookAccountVO.class);
        return ApiResponse.success("更新成功",vo);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookAccountVO> getBookById(@PathVariable @NotNull Long id) {
        BookAccount book = bookAccountService.getById(id);
        if(book == null){
            return ApiResponse.error("图书不存在");
        }
        BookAccountVO vo = new BookAccountVO();
        BeanUtils.copyProperties(book, vo);
        return ApiResponse.success("获取图书成功", vo);
    }

    @PatchMapping("/{id}/stock/adjust")
    public ApiResponse<BookAccountVO> adjustStock(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody StockAdjustDTO dto) {
        BookAccount old = bookAccountService.getById(id);
        if (old == null) return ApiResponse.error("图书不存在");
        if (old.getDeleted() == 1) return ApiResponse.error("图书已删除，无法调整库存");

        int newStock = old.getStock() + dto.getQuantity();
        if (newStock < 0) return ApiResponse.error("库存不足");
        old.setStock(newStock);
        if (old.getVersion() == null) old.setVersion(0);

        BookAccountVO vo = ConvertUtil.convert(old, BookAccountVO.class);
        return ApiResponse.success("调整成功", vo);
    }

    @GetMapping("/page")
    public ApiResponse<IPage<BookAccountVO>> getBookPage(BookPageQuery query){
        Page<BookAccount> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<BookAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(BookAccount::getCreateTime);

        IPage<BookAccount> ipage = bookAccountService.page(page, wrapper);
        if (ipage.getTotal() == 0){
            return ApiResponse.error("查询失败，无图书信息");
        }

        IPage<BookAccountVO> voPage = ipage.convert(e -> ConvertUtil.convert(e, BookAccountVO.class));

        return ApiResponse.success("查询成功",voPage);
    }

    @GetMapping("/exists/isbn/{isbn}")
    public ApiResponse<Boolean> checkIsbnExists(@PathVariable String isbn) {
        boolean exists = bookAccountService.count(
                new LambdaQueryWrapper<BookAccount>().eq(BookAccount::getIsbn, isbn)) > 0;
        return ApiResponse.success("查询结果",exists);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable @NotNull Long id) {
        BookAccount book = bookAccountService.getById(id);
        if (book == null) return ApiResponse.error("图书不存在");
        boolean ok = bookAccountService.removeById(id);
        if (!ok) return ApiResponse.error("删除失败");
        return ApiResponse.success("删除完毕");
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<BookAccountVO> restoreBook(@PathVariable @NotNull Long id) {
        BookAccount book = bookAccountService.getOne(
                new LambdaQueryWrapper<BookAccount>().eq(BookAccount::getId, id));
        if (book == null) {
            return ApiResponse.error("图书 ID 不存在");
        }
        if (book.getDeleted() == 0) {
            return ApiResponse.error("图书已恢复，无需重复操作");
        }

        book.setDeleted(0);
        boolean ok = bookAccountService.updateById(book);
        if (!ok) return ApiResponse.error("恢复失败");

        BookAccountVO vo = ConvertUtil.convert(book, BookAccountVO.class);
        return ApiResponse.success("恢复成功", vo);
    }
}
