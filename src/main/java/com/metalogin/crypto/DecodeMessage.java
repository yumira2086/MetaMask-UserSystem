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
	 * 这里将校验这个签名是否与账户地址匹配，
	 * 即证明账户的所有权
	 */
	public static void main(String[] args) {
		String signedData = "0x9ec7b1ca99f83ccb3db9cece736225e497c52b05d33b" +
				"f6fd4d9d090234d6c5184af5f0a19bedfab55bbaf0814b77e26bb19ad3" +
				"0dcf25aeb651c5df4778d8c2771c";
		System.out.println(verifySignature("0x3aa33a9422a7b607f93a8bdf560d5d11d6640846",
				"123", signedData));
	}

	/**
	 * 校验以太坊签名是否合法
	 * 先通过 signature 解码出 v，r，s 签名数据，再求出公钥，再求出地址并校验
	 * @param from 发送者地址
	 * @param resource 被签名的原数据
	 * @param signature	签名
	 * @return
	 */
	public static boolean verifySignature(String from,String resource,String signature) {

		//  这里一个字符都不能少，eth签名规则必须拼上 "\u0019Ethereum Signed Message:\n"
		String prefix = "\u0019Ethereum Signed Message:\n" + resource.length();
		byte[] msgHash = Hash.sha3((prefix + resource).getBytes());

		byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
		//取出v
		byte v = signatureBytes[64];
		if (v < 27) {
			v += 27;
		}
		//取出r,s,和v一起可以求出公钥
		Sign.SignatureData sd = new Sign.SignatureData(
				v,
				Arrays.copyOfRange(signatureBytes, 0, 32),
				Arrays.copyOfRange(signatureBytes, 32, 64));

		String addressRecovered = null;
		boolean match = false;
		// 遍历recId
		for (int i = 0; i < 4; i++) {
			//计算公钥
			BigInteger publicKey = Sign.recoverFromSignature(
					(byte) i,
					new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
					msgHash);
			if (publicKey != null) {
				//通过公钥计算地址
				addressRecovered = "0x" + Keys.getAddress(publicKey);
				if (addressRecovered.equals(from)) {
					match = true;
					break;
				}
			}
		}

		logger.info("verify:"+resource +" : "+signature +" ==> "+addressRecovered);
		return match;
	}

}
