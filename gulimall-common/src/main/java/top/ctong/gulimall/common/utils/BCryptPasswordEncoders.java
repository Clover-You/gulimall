package top.ctong.gulimall.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀     ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒      ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░      ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄      ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄     ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒     ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 * Copyright 2022 Clover You.
 * <p>
 * 密码加/解密工具，依赖于SpringBoot
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-11 7:31 下午
 */
public class BCryptPasswordEncoders {
    private BCryptPasswordEncoders() {
    }

    /**
     * 比较一个明文和一个密文是否相同
     * @param rawPassword  明文
     * @param encodedPassword 密文
     * @return boolean 比较结果true为相同反之为false
     * @author Clover You
     * @date 2022/2/11 7:35 下午
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /** 
     * 将一个明文进行盐值加密，加密后不可逆
     * @param rawPassword 明文
     * @return String 返回加密后的密文
     * @author Clover You 
     * @date 2022/2/11 7:37 下午
     */
    public static String encode(CharSequence rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
