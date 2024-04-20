package com.pointofsales.miniproject.service;

import com.pointofsales.miniproject.model.dto.TransactionOnlyDto;
import com.pointofsales.miniproject.model.entity.Transaction;
import com.pointofsales.miniproject.model.entity.TransactionDetail;
import com.pointofsales.miniproject.repository.TransactionDetailsRepo;
import com.pointofsales.miniproject.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo tranRepo;

    @Autowired
    TransactionDetailsRepo detRepo;



    public List<TransactionOnlyDto> listTransaction(){

        List<Transaction> tr = tranRepo.findAll();
        List<TransactionOnlyDto> tro = new ArrayList<TransactionOnlyDto>();
        for (Transaction t : tr) {
            tro.add(t.entityToDto());
        }

        return tro;

    }

    public List<TransactionDetail> listTransactionDetail(int id){

        //List<Integer> ids = new ArrayList<Integer>();
        //ids.add(id);
        Transaction tr = tranRepo.findById(id).get();
        List<TransactionDetail> det = tr.getTransactionDetail();
    return det;//detRepo.findAllById(det);

    }

    public boolean addTransaction(Transaction tr){
        List<TransactionDetail> trdet = tr.getTransactionDetail();
        tr.setTransactionDetail(null);
        tranRepo.save(tr);
        int id=tranRepo.getMaxId();
        for (TransactionDetail det: trdet) {
            det.setTransactionId(id);
        }
        detRepo.saveAll(trdet);
        return true;
    }

}
