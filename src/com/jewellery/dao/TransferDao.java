package com.jewellery.dao;
import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.Transfer;

	public interface TransferDao {
	public List<Transfer> listTransfer();
	public Transfer getTransfer(Integer id);
	public void insertTransfer(Transfer transfer);
	public void updateTransfer(Transfer transfer);
	public void deleteTransfer(Transfer transfer);
	public BigInteger getTransferCode();
}
