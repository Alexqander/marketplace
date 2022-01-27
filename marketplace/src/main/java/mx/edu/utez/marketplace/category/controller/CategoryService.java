package mx.edu.utez.marketplace.category.controller;

import mx.edu.utez.marketplace.category.model.Category;
import mx.edu.utez.marketplace.category.model.CategoryRepository;
import mx.edu.utez.marketplace.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Transactional
    public ResponseEntity<Message>findAll(){
        return new ResponseEntity<>(new Message("ok",false,categoryRepository.findAll()), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<Message>findById(long id){
        if (categoryRepository.existsById(id)){
            return new ResponseEntity<>(new Message("Ok",false,categoryRepository.findById(id)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("No se encontro la categoria",true,null),HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackOn = {SQLException.class})
    public ResponseEntity<Message> save(Category category){
        Optional<Category>existCategory = categoryRepository.findByDescription(category.getDescription());
        if (existCategory.isPresent()){
            return new ResponseEntity<>(new Message("La cetegoria ya existe papi",true,null),HttpStatus.BAD_REQUEST);

        }
            Category savedCategory = categoryRepository.saveAndFlush(category);
            return new ResponseEntity<>(new Message("Categoria resgistrada",false,savedCategory),HttpStatus.OK);
    }

    @Transactional(rollbackOn = {SQLException.class})
    public ResponseEntity<Message>update(Category cateogory){
        if (categoryRepository.existsById(cateogory.getId())){
            return new ResponseEntity<>(new Message("Categoria actualizda",false,categoryRepository.saveAndFlush(cateogory)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("La categoria no existe",true,0),HttpStatus.BAD_REQUEST);
    }

}
