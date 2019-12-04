from Crypto.Cipher import AES
from binascii import a2b_hex,b2a_hex

class Cryptor:
    #创立一个加密解密用的类
    def __init__(self,key):
        self.key = self.modifyLength(key)
        self.mode = AES.MODE_CBC
   
    def modifyLength(self,s):
        length = len(s)
        if length %16 == 0:
            add = 0
        else:
            add = 16 - ( length % 16 )
        return s + '\0' * add

    def encrypt(self,text):
        text = self.modifyLength(text)
        cryptor = AES.new(self.key,self.mode,b'0000000000000000')
        rawText = cryptor.encrypt(text)
        return b2a_hex(rawText)[0]

    def decrypt(self,text):
        text = self.modifyLength(text)
        cryptor = AES.new(self.key,self.mode,b'0000000000000000')
        plain_text = cryptor.decrypt(a2b_hex(text))
        return plain_text.rsplit('\0')[0]

key="ewdeqwdewwrefgerg"
a = Cryptor(key)
print(a.encrypt("123456"))
