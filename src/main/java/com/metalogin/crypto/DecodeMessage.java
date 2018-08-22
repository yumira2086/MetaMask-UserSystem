package com.metalogin.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.*;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class DecodeMessage {

	public static Logger logger = LoggerFactory.getLogger(DecodeMessage.class);

	/**
	 * 通过签名后会得到一个加密后的字符串
	 * 本类将分析这个字符串
	 */
	public static void main(String[] args) {
//		String signedData = "0x0134f9d60d0030a711b9badfa23ed6a0a0e6f08797c7d744a89aff55b72dab44764267c163a6281888fc30ce5d08c8f06c807d525cf9a48584666700d478a0fc1b";
//		System.out.println(verifySignature("0x9dd2c369a187b4e6b9c402f030e50743e619301ea62aa4c0737d4ef7e10a3d49", signedData));
	}

	/**
	 * 校验以太坊签名是否合法
	 * 先通过 signature 解码出 v，r，s 签名数据，再求出公钥，再求出地址并校验
	 * @param resource 被签名的原数据
	 * @param signature	签名
	 * @return
	 */
	public static String verifySignature(String resource,String signature) {
		//  这里一个字符都不能少，eth签名规则必须拼上 "\u0019Ethereum Signed Message:\n"
		String prefix = "\u0019Ethereum Signed Message:\n" + resource.length();
		byte[] msgHash = Hash.sha3((prefix + resource).getBytes());

		byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
		//求出v，r，s
		byte v = signatureBytes[64];
		if (v < 27) {
			v += 27;
		}

		Sign.SignatureData sd = new Sign.SignatureData(
				v,
				Arrays.copyOfRange(signatureBytes, 0, 32),
				Arrays.copyOfRange(signatureBytes, 32, 64));

		String addressRecovered = null;
		// 遍历recId
		for (int i = 0; i < 4; i++) {
			BigInteger publicKey = Sign.recoverFromSignature(
					(byte) i,
					new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
					msgHash);

			if (publicKey != null) {
				addressRecovered = "0x" + Keys.getAddress(publicKey);
                break;
			}
		}
		logger.info("verify:"+resource +" : "+signature +" ==> "+addressRecovered);
		return addressRecovered;
	}

	

}
