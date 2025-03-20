package com.wrapper.proxyapplication;

import android.os.Process;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {
    public static String CPUABI = null;
    static final int ERROR_EXCEPTION = -2;
    static final int ERROR_FALSE = 0;
    static final int ERROR_FILE_EXIST = 2;
    static final int ERROR_FILE_NOT_FOUND = -1;
    static final int ERROR_FILE_NOT_FOUND_INZIP = -3;
    static final int ERROR_SUCCESS = 1;
    public static int MAX_DEX_NUM = 300;
    public static String TAG = "Util";
    public static String dexname = "classes.dex";
    public static boolean ifoverwrite = true;
    public static String libname;
    public static String securename0 = "00O000ll111l.dex";
    public static String securename1 = "00O000ll111l.jar";
    public static String securename11 = ".flag00O000ll111l.vdex";
    public static String securename14 = "00O000ll111l.vdex";
    public static String securename15 = "00O000ll111l.odex";
    public static String securename2 = "000O00ll111l.dex";
    public static String securename3 = "0000000lllll.dex";
    public static String securename4 = "000000olllll.dex";
    public static String securename5 = "0OO00l111l1l";
    public static String securename6 = "o0oooOO0ooOo.dat";
    public static String securename7 = "exportService.txt";
    public static String securename8 = ".flag00O000ll111l.dex";
    public static String securename9 = ".updateIV.dat";
    public static String simplelibname;
    public static String versionname = "tosversion";

    static {
        CPUABI = null;
        libname = "";
        simplelibname = "";
        int tid = Process.myTid();
        CPUABI = getelffilearch("/proc/" + tid + "/exe");
        String str = CPUABI;
        if (str == "86" || str == "86_64") {
            simplelibname = "shellx-super.com.biomes.vanced";
            libname = "lib" + simplelibname + ".so";
            return;
        }
        simplelibname = "shell-super.com.biomes.vanced";
        libname = "lib" + simplelibname + ".so";
    }

    public static int readelfarch(String filename) {
        int c = ERROR_FALSE;
        RandomAccessFile rf = null;
        try {
            RandomAccessFile rf2 = new RandomAccessFile(filename, "r");
            rf2.seek(18);
            c = rf2.read();
            try {
                rf2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            if (rf != null) {
                rf.close();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            if (rf != null) {
                rf.close();
            }
        } catch (Throwable th) {
            if (rf != null) {
                try {
                    rf.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
        return c;
    }

    public static String getelffilearch(String elffile) {
        int archcode = readelfarch(elffile);
        if (archcode == 3) {
            return "86";
        }
        if (archcode == 40) {
            return "armeabi";
        }
        if (archcode == 62) {
            return "86_64";
        }
        if (archcode != 183) {
            return "unknown";
        }
        return "arm64-v8a";
    }

    public static int DeleteFile(String filepath) {
        File tmpfile = new File(filepath);
        if (!tmpfile.exists()) {
            return ERROR_FILE_NOT_FOUND;
        }
        if (!tmpfile.delete()) {
            return ERROR_EXCEPTION;
        }
        return ERROR_SUCCESS;
    }

    public static boolean UnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir) {
        BufferedOutputStream Output_fos = null;
        BufferedInputStream bufbr = null;
        try {
            ZipEntry ze = zf.getEntry(filepathinzip);
            if (ze == null) {
                if (Output_fos != null) {
                    try {
                        Output_fos.close();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return false;
                            }
                        }
                        return false;
                    } catch (Throwable e4) {
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                return false;
                            }
                        }
                        throw e4;
                    }
                }
                return false;
            }
            Output_fos = new BufferedOutputStream(new FileOutputStream(fileinfiledir));
            byte[] buf = new byte[65536];
            bufbr = new BufferedInputStream(zf.getInputStream(ze));
            while (true) {
                int readlen = bufbr.read(buf);
                if (readlen < 0) {
                    try {
                        Output_fos.close();
                        try {
                            bufbr.close();
                            return true;
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            return false;
                        }
                    } catch (IOException e7) {
                        e7.printStackTrace();
                        try {
                            bufbr.close();
                            return false;
                        } catch (IOException e8) {
                            e8.printStackTrace();
                            return false;
                        }
                    } catch (Throwable e9) {
                        try {
                            bufbr.close();
                            throw e9;
                        } catch (IOException e10) {
                            e10.printStackTrace();
                            return false;
                        }
                    }
                } else {
                    Output_fos.write(buf, ERROR_FALSE, readlen);
                }
            }
        } catch (Exception e11) {
            e11.printStackTrace();
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e12) {
                            e12.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e13) {
                    e13.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e14) {
                            e14.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable e15) {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e16) {
                            e16.printStackTrace();
                            return false;
                        }
                    }
                    throw e15;
                }
            }
            return false;
        } catch (Throwable th) {
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e17) {
                            e17.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e18) {
                    e18.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e19) {
                            e19.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable e20) {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e21) {
                            e21.printStackTrace();
                            return false;
                        }
                    }
                    throw e20;
                }
            }
            throw th;
        }
    }

    public static int Comparetxtinzip(ZipFile apkzf, String filepathinzip, File fileinfiledir) {
        int result;
        BufferedInputStream checkzbr = null;
        BufferedInputStream checkfbr = null;
        ZipEntry cookie_entry = apkzf.getEntry(filepathinzip);
        if (cookie_entry == null) {
            if (checkzbr != null) {
                try {
                    checkzbr.close();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    return ERROR_EXCEPTION;
                } catch (Throwable e4) {
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    throw e4;
                }
            }
            return ERROR_FILE_NOT_FOUND_INZIP;
        }
        try {
            byte[] checkzipbuf = new byte[1024];
            byte[] checkfilebuf = new byte[1024];
            BufferedInputStream checkzbr2 = new BufferedInputStream(apkzf.getInputStream(cookie_entry));
            String tmpzipstr = new String(checkzipbuf).substring(ERROR_FALSE, checkzbr2.read(checkzipbuf));
            BufferedInputStream checkfbr2 = new BufferedInputStream(new FileInputStream(fileinfiledir));
            if (new String(checkfilebuf).substring(ERROR_FALSE, checkfbr2.read(checkfilebuf)).equals(tmpzipstr)) {
                result = ERROR_SUCCESS;
            } else {
                result = ERROR_FALSE;
            }
            try {
                checkzbr2.close();
                try {
                    checkfbr2.close();
                    return result;
                } catch (IOException e6) {
                    e6.printStackTrace();
                    return ERROR_EXCEPTION;
                }
            } catch (IOException e7) {
                e7.printStackTrace();
                try {
                    checkfbr2.close();
                    return ERROR_EXCEPTION;
                } catch (IOException e8) {
                    e8.printStackTrace();
                    return ERROR_EXCEPTION;
                }
            } catch (Throwable e9) {
                try {
                    checkfbr2.close();
                    throw e9;
                } catch (IOException e10) {
                    e10.printStackTrace();
                    return ERROR_EXCEPTION;
                }
            }
        } catch (Exception e11) {
            e11.printStackTrace();
            if (checkzbr != null) {
                try {
                    checkzbr.close();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e12) {
                            e12.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                } catch (IOException e13) {
                    e13.printStackTrace();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e14) {
                            e14.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    return ERROR_EXCEPTION;
                } catch (Throwable e15) {
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e16) {
                            e16.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    throw e15;
                }
            }
            return ERROR_EXCEPTION;
        } catch (Throwable e17) {
            if (checkzbr != null) {
                try {
                    checkzbr.close();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                } catch (IOException e19) {
                    e19.printStackTrace();
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e20) {
                            e20.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    return ERROR_EXCEPTION;
                } catch (Throwable e21) {
                    if (checkfbr != null) {
                        try {
                            checkfbr.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                            return ERROR_EXCEPTION;
                        }
                    }
                    throw e21;
                }
            }
            throw e17;
        }
    }

    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = ERROR_FALSE; i < children.length; i += ERROR_SUCCESS) {
                if (!deleteDir(new File(file, children[i]))) {
                    return false;
                }
            }
        }
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:486:0x1119  */
    /* JADX WARNING: Removed duplicated region for block: B:490:0x116b  */
    /* JADX WARNING: Removed duplicated region for block: B:493:0x1185  */
    /* JADX WARNING: Removed duplicated region for block: B:498:0x11e5  */
    /* JADX WARNING: Removed duplicated region for block: B:507:0x124a A[SYNTHETIC, Splitter:B:507:0x124a] */
    /* JADX WARNING: Removed duplicated region for block: B:613:0x132b  */
    /* JADX WARNING: Removed duplicated region for block: B:618:0x1389  */
    /* JADX WARNING: Removed duplicated region for block: B:623:0x13e9  */
    /* JADX WARNING: Removed duplicated region for block: B:632:0x144e A[SYNTHETIC, Splitter:B:632:0x144e] */
    /* JADX WARNING: Removed duplicated region for block: B:757:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int PrepareSecurefiles(android.content.Context r32, java.util.zip.ZipFile r33) {
        /*
            r1 = r33
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.File r7 = r32.getFilesDir()
            java.lang.String r7 = r7.getAbsolutePath()
            r6.append(r7)
            java.lang.String r7 = "/prodexdir"
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.io.File r7 = new java.io.File
            r7.<init>(r6)
            boolean r8 = r7.isDirectory()
            if (r8 != 0) goto L_0x002d
            r7.mkdir()
        L_0x002d:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            java.lang.String r9 = "/"
            r8.append(r9)
            java.lang.String r10 = versionname
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r6)
            java.lang.String r11 = "/backUp"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            java.lang.String r12 = "/firstLoad"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "assets/"
            r12.append(r13)
            java.lang.String r14 = versionname
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "t"
            r14.append(r15)
            java.lang.String r15 = CPUABI
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            java.io.RandomAccessFile r15 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x10ef, all -> 0x10d6 }
            r16 = r2
            java.lang.String r2 = "rw"
            r15.<init>(r8, r2)     // Catch:{ Exception -> 0x10c6, all -> 0x10af }
            r5 = r15
            java.nio.channels.FileChannel r2 = r5.getChannel()     // Catch:{ Exception -> 0x109c, all -> 0x1085 }
            r3 = r2
            java.nio.channels.FileLock r2 = r3.lock()     // Catch:{ Exception -> 0x1070, all -> 0x1059 }
            r4 = r2
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x1042, all -> 0x102b }
            r2.<init>(r8)     // Catch:{ Exception -> 0x1042, all -> 0x102b }
            long r16 = r2.length()     // Catch:{ Exception -> 0x1014, all -> 0x0ffd }
            r18 = 0
            int r20 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r20 == 0) goto L_0x041f
            int r16 = Comparetxtinzip(r1, r12, r2)     // Catch:{ Exception -> 0x040c, all -> 0x03f3 }
            r17 = r16
            r15 = 1
            r18 = r2
            r2 = r17
            if (r2 != r15) goto L_0x03b7
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03a6, all -> 0x038f }
            r15.<init>()     // Catch:{ Exception -> 0x03a6, all -> 0x038f }
            r15.append(r13)     // Catch:{ Exception -> 0x03a6, all -> 0x038f }
            r17 = r7
            java.lang.String r7 = securename5     // Catch:{ Exception -> 0x0380, all -> 0x036b }
            r15.append(r7)     // Catch:{ Exception -> 0x0380, all -> 0x036b }
            java.lang.String r7 = r15.toString()     // Catch:{ Exception -> 0x0380, all -> 0x036b }
            java.io.File r15 = new java.io.File     // Catch:{ Exception -> 0x0380, all -> 0x036b }
            r19 = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x035b, all -> 0x0345 }
            r11.<init>()     // Catch:{ Exception -> 0x035b, all -> 0x0345 }
            r11.append(r6)     // Catch:{ Exception -> 0x035b, all -> 0x0345 }
            r11.append(r9)     // Catch:{ Exception -> 0x035b, all -> 0x0345 }
            r20 = r10
            java.lang.String r10 = securename5     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r11.append(r10)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.String r10 = r11.toString()     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r15.<init>(r10)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            boolean r7 = checkCopiedFileCrc(r1, r7, r15)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            if (r7 == 0) goto L_0x0320
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r7.<init>()     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r7.append(r13)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.String r10 = securename6     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r7.append(r10)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r11.<init>()     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r11.append(r6)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r11.append(r9)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.String r15 = securename6     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r11.append(r15)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            boolean r7 = checkCopiedFileCrc(r1, r7, r10)     // Catch:{ Exception -> 0x0337, all -> 0x0323 }
            if (r7 == 0) goto L_0x031d
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r13)
            r10.append(r14)
            java.lang.String r10 = r10.toString()
            java.util.zip.ZipEntry r10 = r1.getEntry(r10)
            if (r10 == 0) goto L_0x0187
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            java.lang.String r15 = libname
            r11.append(r15)
            java.lang.String r11 = r11.toString()
            r15 = r8
            long r7 = r10.getSize()
            boolean r7 = isFileValid(r11, r7)
            if (r7 != 0) goto L_0x0184
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r13)
            r7.append(r14)
            java.lang.String r7 = r7.toString()
            java.io.File r8 = new java.io.File
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            r21 = r10
            java.lang.String r10 = libname
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            r8.<init>(r10)
            UnzipFile(r1, r7, r8)
            goto L_0x018a
        L_0x0184:
            r21 = r10
            goto L_0x018a
        L_0x0187:
            r15 = r8
            r21 = r10
        L_0x018a:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r13)
            r7.append(r14)
            java.lang.String r7 = r7.toString()
            java.util.zip.ZipEntry r7 = r1.getEntry(r7)
            if (r7 == 0) goto L_0x01f0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r9)
            java.lang.String r10 = securename6
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            long r10 = r7.getSize()
            boolean r8 = isFileValid(r8, r10)
            if (r8 != 0) goto L_0x01ed
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r13)
            java.lang.String r10 = securename6
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            r21 = r7
            java.lang.String r7 = securename6
            r11.append(r7)
            java.lang.String r7 = r11.toString()
            r10.<init>(r7)
            UnzipFile(r1, r8, r10)
            goto L_0x01f2
        L_0x01ed:
            r21 = r7
            goto L_0x01f2
        L_0x01f0:
            r21 = r7
        L_0x01f2:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r13)
            r7.append(r14)
            java.lang.String r7 = r7.toString()
            java.util.zip.ZipEntry r7 = r1.getEntry(r7)
            if (r7 == 0) goto L_0x0252
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r9)
            java.lang.String r10 = securename7
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            long r10 = r7.getSize()
            boolean r8 = isFileValid(r8, r10)
            if (r8 != 0) goto L_0x0252
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r13)
            java.lang.String r10 = securename7
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            java.lang.String r9 = securename7
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            r10.<init>(r9)
            UnzipFile(r1, r8, r10)
        L_0x0252:
            java.util.zip.ZipEntry r7 = r1.getEntry(r12)
            if (r7 == 0) goto L_0x026c
            long r8 = r7.getSize()
            r10 = r15
            boolean r8 = isFileValid(r10, r8)
            if (r8 != 0) goto L_0x026d
            java.io.File r8 = new java.io.File
            r8.<init>(r10)
            UnzipFile(r1, r12, r8)
            goto L_0x026d
        L_0x026c:
            r10 = r15
        L_0x026d:
            if (r4 == 0) goto L_0x031b
            r4.release()     // Catch:{ IOException -> 0x02ab }
            if (r3 == 0) goto L_0x031b
            r3.close()     // Catch:{ IOException -> 0x0288 }
            r5.close()     // Catch:{ IOException -> 0x027e }
            goto L_0x031b
        L_0x027e:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()
            r9 = -1
            return r9
        L_0x0285:
            r0 = move-exception
            r8 = r0
            goto L_0x029c
        L_0x0288:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()     // Catch:{ all -> 0x0285 }
            r5.close()     // Catch:{ IOException -> 0x0295 }
        L_0x0292:
            r9 = -1
            return r9
        L_0x0295:
            r0 = move-exception
            r9 = -1
            r11 = r0
            r11.printStackTrace()
            return r9
        L_0x029c:
            r5.close()     // Catch:{ IOException -> 0x02a1 }
            throw r8
        L_0x02a1:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()
            r9 = -1
            return r9
        L_0x02a8:
            r0 = move-exception
            r8 = r0
            goto L_0x02e8
        L_0x02ab:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()     // Catch:{ all -> 0x02a8 }
            if (r3 == 0) goto L_0x02e6
            r3.close()     // Catch:{ IOException -> 0x02c6 }
            r5.close()     // Catch:{ IOException -> 0x02bc }
            r11 = -1
            goto L_0x02e7
        L_0x02bc:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
            r11 = -1
            return r11
        L_0x02c3:
            r0 = move-exception
            r9 = r0
            goto L_0x02da
        L_0x02c6:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()     // Catch:{ all -> 0x02c3 }
            r5.close()     // Catch:{ IOException -> 0x02d3 }
            r11 = -1
            return r11
        L_0x02d3:
            r0 = move-exception
            r11 = -1
            r13 = r0
            r13.printStackTrace()
            return r11
        L_0x02da:
            r5.close()     // Catch:{ IOException -> 0x02df }
            throw r9
        L_0x02df:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
            r11 = -1
            return r11
        L_0x02e6:
            r11 = -1
        L_0x02e7:
            return r11
        L_0x02e8:
            if (r3 == 0) goto L_0x031a
            r3.close()     // Catch:{ IOException -> 0x02fc }
            r5.close()     // Catch:{ IOException -> 0x02f2 }
            goto L_0x031a
        L_0x02f2:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()
            r9 = -1
            return r9
        L_0x02f9:
            r0 = move-exception
            r8 = r0
            goto L_0x030e
        L_0x02fc:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()     // Catch:{ all -> 0x02f9 }
            r5.close()     // Catch:{ IOException -> 0x0307 }
            goto L_0x0292
        L_0x0307:
            r0 = move-exception
            r9 = -1
            r11 = r0
            r11.printStackTrace()
            return r9
        L_0x030e:
            r5.close()     // Catch:{ IOException -> 0x0313 }
            throw r8
        L_0x0313:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()
            r9 = -1
            return r9
        L_0x031a:
            throw r8
        L_0x031b:
            r7 = 2
            return r7
        L_0x031d:
            r10 = r8
            goto L_0x03be
        L_0x0320:
            r10 = r8
            goto L_0x03be
        L_0x0323:
            r0 = move-exception
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r11 = r12
            r2 = r13
            r10 = r19
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1316
        L_0x0337:
            r0 = move-exception
            r7 = r1
            r11 = r12
            r2 = r13
            r10 = r19
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1100
        L_0x0345:
            r0 = move-exception
            r20 = r10
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r11 = r12
            r2 = r13
            r10 = r19
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1316
        L_0x035b:
            r0 = move-exception
            r20 = r10
            r7 = r1
            r11 = r12
            r2 = r13
            r10 = r19
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1100
        L_0x036b:
            r0 = move-exception
            r20 = r10
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1316
        L_0x0380:
            r0 = move-exception
            r20 = r10
            r7 = r1
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1100
        L_0x038f:
            r0 = move-exception
            r17 = r7
            r20 = r10
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1316
        L_0x03a6:
            r0 = move-exception
            r17 = r7
            r20 = r10
            r7 = r1
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1100
        L_0x03b7:
            r17 = r7
            r20 = r10
            r19 = r11
            r10 = r8
        L_0x03be:
            r7 = -1
            if (r2 == r7) goto L_0x03c4
            r7 = -3
            if (r2 != r7) goto L_0x0428
        L_0x03c4:
            int r7 = android.os.Process.myPid()     // Catch:{ Exception -> 0x03e5, all -> 0x03d1 }
            android.os.Process.killProcess(r7)     // Catch:{ Exception -> 0x03e5, all -> 0x03d1 }
            r7 = 0
            java.lang.System.exit(r7)     // Catch:{ Exception -> 0x03e5, all -> 0x03d1 }
            goto L_0x0428
        L_0x03d1:
            r0 = move-exception
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x03e5:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x03f3:
            r0 = move-exception
            r18 = r2
            r17 = r7
            r20 = r10
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1316
        L_0x040c:
            r0 = move-exception
            r18 = r2
            r17 = r7
            r20 = r10
            r7 = r1
            r10 = r11
            r11 = r12
            r2 = r13
            r22 = r20
            r1 = r0
            r12 = r8
            r8 = r6
            r6 = r14
            goto L_0x1100
        L_0x041f:
            r18 = r2
            r17 = r7
            r20 = r10
            r19 = r11
            r10 = r8
        L_0x0428:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.<init>()     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r6)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r9)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            java.lang.String r7 = libname     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r7)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.<init>()     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r6)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r9)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            java.lang.String r7 = securename6     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2.append(r7)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r2 = -1
            r7 = -1
            r8 = -1
            r11 = -1
            r15 = 0
            r15 = 0
        L_0x045c:
            r21 = r2
            int r2 = MAX_DEX_NUM     // Catch:{ Exception -> 0x0fe9, all -> 0x0fd5 }
            r22 = r7
            java.lang.String r7 = "/odexdir/"
            r23 = r8
            java.lang.String r8 = "/oat/arm64/"
            r24 = r11
            java.lang.String r11 = "/oat/arm/"
            r25 = r5
            java.lang.String r5 = "_"
            r26 = r3
            java.lang.String r3 = "."
            if (r15 >= r2) goto L_0x0713
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ff, all -> 0x06ef }
            r2.<init>()     // Catch:{ Exception -> 0x06ff, all -> 0x06ef }
            r2.append(r6)     // Catch:{ Exception -> 0x06ff, all -> 0x06ef }
            r2.append(r9)     // Catch:{ Exception -> 0x06ff, all -> 0x06ef }
            r27 = r4
            java.lang.String r4 = securename0     // Catch:{ Exception -> 0x06db, all -> 0x06cd }
            r28 = r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r14.<init>()     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r14.append(r5)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r14.append(r15)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            java.lang.String r4 = CreatenewFileName(r4, r3, r14)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r2.append(r4)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            int r2 = DeleteFile(r2)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r4.<init>()     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r4.append(r6)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r4.append(r9)     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            java.lang.String r14 = securename1     // Catch:{ Exception -> 0x06b8, all -> 0x06a9 }
            r29 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.<init>()     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.append(r5)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.append(r15)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            java.lang.String r12 = CreatenewFileName(r14, r3, r12)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r4.append(r12)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            int r4 = DeleteFile(r4)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.<init>()     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.append(r6)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r12.append(r7)     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            java.lang.String r14 = securename0     // Catch:{ Exception -> 0x0693, all -> 0x0683 }
            r30 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r10.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r10.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r10.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r10 = CreatenewFileName(r14, r3, r10)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r10)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r10 = r12.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            int r10 = DeleteFile(r10)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = securename8     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r31 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = CreatenewFileName(r14, r3, r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r12.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            int r7 = DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r11)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = securename11     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r21 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = CreatenewFileName(r14, r3, r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r12.append(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r12.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r8)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = securename11     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = CreatenewFileName(r12, r3, r14)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r12)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r11)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = CreatenewFileName(r12, r3, r14)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r12)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r11)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = CreatenewFileName(r12, r3, r14)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r12)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r8)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = CreatenewFileName(r12, r3, r14)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r12)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r6)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r8)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.<init>()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r5)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r14.append(r15)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r12 = CreatenewFileName(r12, r3, r14)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7.append(r12)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            DeleteFile(r7)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r7 = -1
            if (r7 != r2) goto L_0x0635
            if (r7 != r4) goto L_0x0635
            if (r7 != r10) goto L_0x0635
            r23 = r10
            goto L_0x0723
        L_0x0635:
            r3 = -2
            if (r3 == r2) goto L_0x063c
            if (r3 == r4) goto L_0x063c
            if (r3 != r10) goto L_0x0647
        L_0x063c:
            int r3 = android.os.Process.myPid()     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            android.os.Process.killProcess(r3)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
            r3 = 0
            java.lang.System.exit(r3)     // Catch:{ Exception -> 0x066c, all -> 0x065b }
        L_0x0647:
            int r15 = r15 + 1
            r7 = r4
            r8 = r10
            r11 = r21
            r5 = r25
            r3 = r26
            r4 = r27
            r14 = r28
            r12 = r29
            r10 = r30
            goto L_0x045c
        L_0x065b:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r12 = r30
            r1 = r0
            goto L_0x1316
        L_0x066c:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r2 = r13
            r10 = r19
            r22 = r20
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            r1 = r0
            goto L_0x1100
        L_0x0683:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r12 = r10
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r1 = r0
            goto L_0x1316
        L_0x0693:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r12 = r10
            r2 = r13
            r10 = r19
            r22 = r20
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r1 = r0
            goto L_0x1100
        L_0x06a9:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r6 = r28
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x06b8:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x06cd:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x06db:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r5 = r25
            r3 = r26
            r4 = r27
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x06ef:
            r0 = move-exception
            r27 = r4
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x06ff:
            r0 = move-exception
            r27 = r4
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r5 = r25
            r3 = r26
            r1 = r0
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x0713:
            r27 = r4
            r31 = r7
            r30 = r10
            r29 = r12
            r28 = r14
            r2 = r21
            r4 = r22
            r21 = r24
        L_0x0723:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = securename9     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            DeleteFile(r7)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = securename5     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            DeleteFile(r7)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r13)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = securename5     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r14 = securename5     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r14)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r10.<init>(r12)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            UnzipFile(r1, r7, r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r13)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = libname     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r14 = libname     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r14)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r10.<init>(r12)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            UnzipFile(r1, r7, r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r13)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = securename6     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r14 = securename6     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r14)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r10.<init>(r12)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            UnzipFile(r1, r7, r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r13)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r10 = securename7     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r7.append(r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.<init>()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r6)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r9)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r14 = securename7     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r12.append(r14)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r10.<init>(r12)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            UnzipFile(r1, r7, r10)     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0fbe, all -> 0x0fad }
            r10 = r30
            r7.<init>(r10)     // Catch:{ Exception -> 0x0f97, all -> 0x0f87 }
            r12 = r29
            UnzipFile(r1, r12, r7)     // Catch:{ Exception -> 0x0f72, all -> 0x0f63 }
            r7 = 0
        L_0x0812:
            if (r7 >= r15) goto L_0x0af9
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0ae3, all -> 0x0ad3 }
            r14.<init>()     // Catch:{ Exception -> 0x0ae3, all -> 0x0ad3 }
            r22 = r2
            r2 = r20
            r14.append(r2)     // Catch:{ Exception -> 0x0abd, all -> 0x0aad }
            r14.append(r9)     // Catch:{ Exception -> 0x0abd, all -> 0x0aad }
            r20 = r4
            java.lang.String r4 = securename0     // Catch:{ Exception -> 0x0abd, all -> 0x0aad }
            r30 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.<init>()     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.append(r5)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.append(r7)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            java.lang.String r4 = CreatenewFileName(r4, r3, r10)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r14.append(r4)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            java.lang.String r4 = r14.toString()     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            int r4 = DeleteFile(r4)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.<init>()     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.append(r2)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r10.append(r9)     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            java.lang.String r14 = securename1     // Catch:{ Exception -> 0x0a96, all -> 0x0a85 }
            r29 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r12.<init>()     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r12.append(r5)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r12.append(r7)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            java.lang.String r12 = CreatenewFileName(r14, r3, r12)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r10.append(r12)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            int r10 = DeleteFile(r10)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r12.<init>()     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r12.append(r2)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r14 = r31
            r12.append(r14)     // Catch:{ Exception -> 0x0a6d, all -> 0x0a5b }
            r24 = r6
            java.lang.String r6 = securename0     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r1.<init>()     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r1.append(r5)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r1.append(r7)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.String r1 = CreatenewFileName(r6, r3, r1)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r12.append(r1)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.String r1 = r12.toString()     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            int r1 = DeleteFile(r1)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r6.<init>()     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r6.append(r2)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r6.append(r14)     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            java.lang.String r12 = securename8     // Catch:{ Exception -> 0x0a42, all -> 0x0a2f }
            r31 = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            int r6 = DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r21 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r11)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename11     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r8)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename11     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r11)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r11)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r8)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r2)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r8)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.<init>()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r5)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r13.append(r7)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6.append(r12)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            DeleteFile(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6 = -1
            if (r6 != r4) goto L_0x09d9
            if (r6 != r10) goto L_0x09d9
            if (r6 != r1) goto L_0x09d9
            r20 = r10
            goto L_0x0b0d
        L_0x09d9:
            r6 = -2
            if (r6 == r4) goto L_0x09e0
            if (r6 == r10) goto L_0x09e0
            if (r6 != r1) goto L_0x09eb
        L_0x09e0:
            int r6 = android.os.Process.myPid()     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            android.os.Process.killProcess(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
            r6 = 0
            java.lang.System.exit(r6)     // Catch:{ Exception -> 0x0a15, all -> 0x0a01 }
        L_0x09eb:
            int r7 = r7 + 1
            r23 = r1
            r20 = r2
            r2 = r4
            r4 = r10
            r6 = r24
            r12 = r29
            r10 = r30
            r13 = r31
            r1 = r33
            r31 = r14
            goto L_0x0812
        L_0x0a01:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r10 = r19
            r8 = r24
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1316
        L_0x0a15:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r10 = r19
            r8 = r24
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1100
        L_0x0a2f:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r2 = r13
            r10 = r19
            r8 = r24
            r6 = r28
            r11 = r29
            r12 = r30
            goto L_0x1316
        L_0x0a42:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r2 = r13
            r10 = r19
            r8 = r24
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            goto L_0x1100
        L_0x0a5b:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r2 = r13
            r10 = r19
            r6 = r28
            r11 = r29
            r12 = r30
            goto L_0x1316
        L_0x0a6d:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r2 = r13
            r10 = r19
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            goto L_0x1100
        L_0x0a85:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r11 = r12
            r2 = r13
            r10 = r19
            r6 = r28
            r12 = r30
            goto L_0x1316
        L_0x0a96:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r11 = r12
            r2 = r13
            r10 = r19
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r12 = r30
            goto L_0x1100
        L_0x0aad:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r28
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x0abd:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r22 = r2
            r8 = r6
            r11 = r12
            r2 = r13
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x0ad3:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r6 = r28
            r12 = r10
            r10 = r19
            goto L_0x1316
        L_0x0ae3:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r12 = r10
            r10 = r19
            goto L_0x1100
        L_0x0af9:
            r22 = r2
            r24 = r6
            r30 = r10
            r29 = r12
            r2 = r20
            r14 = r31
            r20 = r4
            r31 = r13
            r4 = r22
            r1 = r23
        L_0x0b0d:
            r6 = 0
        L_0x0b0e:
            if (r6 >= r15) goto L_0x0d64
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d4a, all -> 0x0d36 }
            r7.<init>()     // Catch:{ Exception -> 0x0d4a, all -> 0x0d36 }
            r10 = r19
            r7.append(r10)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.append(r9)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r12 = securename0     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.append(r5)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.append(r6)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.append(r12)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            int r7 = DeleteFile(r7)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r4 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.append(r10)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.append(r9)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r12 = securename1     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.append(r5)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r13.append(r6)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r7.append(r12)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            int r7 = DeleteFile(r7)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.append(r10)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.append(r14)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r13 = securename0     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r19 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r1.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r1.append(r5)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r1.append(r6)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r1 = CreatenewFileName(r13, r3, r1)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.append(r1)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r1 = r12.toString()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            int r1 = DeleteFile(r1)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.<init>()     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.append(r10)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r12.append(r14)     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            java.lang.String r13 = securename8     // Catch:{ Exception -> 0x0d1e, all -> 0x0d0c }
            r22 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = CreatenewFileName(r13, r3, r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r12.append(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r12.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            int r2 = DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r21 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r11)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename11     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r8)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename11     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r11)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r11)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r8)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename14     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r10)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r8)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = securename15     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.<init>()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r5)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r13.append(r6)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r12 = CreatenewFileName(r12, r3, r13)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2.append(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            DeleteFile(r2)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r2 = -1
            if (r2 != r4) goto L_0x0cca
            if (r2 != r7) goto L_0x0cca
            if (r2 != r1) goto L_0x0cca
            goto L_0x0d6a
        L_0x0cca:
            r2 = -2
            if (r2 == r4) goto L_0x0cd1
            if (r2 == r7) goto L_0x0cd1
            if (r2 != r1) goto L_0x0cdc
        L_0x0cd1:
            int r12 = android.os.Process.myPid()     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            android.os.Process.killProcess(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
            r12 = 0
            java.lang.System.exit(r12)     // Catch:{ Exception -> 0x0cf6, all -> 0x0ce6 }
        L_0x0cdc:
            int r6 = r6 + 1
            r20 = r7
            r19 = r10
            r2 = r22
            goto L_0x0b0e
        L_0x0ce6:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r8 = r24
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1316
        L_0x0cf6:
            r0 = move-exception
            r7 = r33
            r1 = r0
            r8 = r24
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1100
        L_0x0d0c:
            r0 = move-exception
            r22 = r2
            r7 = r33
            r1 = r0
            r8 = r24
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1316
        L_0x0d1e:
            r0 = move-exception
            r22 = r2
            r7 = r33
            r1 = r0
            r8 = r24
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1100
        L_0x0d36:
            r0 = move-exception
            r22 = r2
            r10 = r19
            r7 = r33
            r1 = r0
            r8 = r24
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1316
        L_0x0d4a:
            r0 = move-exception
            r22 = r2
            r10 = r19
            r7 = r33
            r1 = r0
            r8 = r24
            r5 = r25
            r3 = r26
            r4 = r27
            r6 = r28
            r11 = r29
            r12 = r30
            r2 = r31
            goto L_0x1100
        L_0x0d64:
            r22 = r2
            r10 = r19
            r19 = r1
        L_0x0d6a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = r31
            r1.append(r2)
            r6 = r28
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r7 = r33
            java.util.zip.ZipEntry r1 = r7.getEntry(r1)
            if (r1 == 0) goto L_0x0dd1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r8 = r24
            r3.append(r8)
            r3.append(r9)
            java.lang.String r4 = libname
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            long r4 = r1.getSize()
            boolean r3 = isFileValid(r3, r4)
            if (r3 != 0) goto L_0x0dd3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r8)
            r5.append(r9)
            java.lang.String r11 = libname
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            UnzipFile(r7, r3, r4)
            goto L_0x0dd3
        L_0x0dd1:
            r8 = r24
        L_0x0dd3:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.util.zip.ZipEntry r1 = r7.getEntry(r3)
            if (r1 == 0) goto L_0x0e33
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r3.append(r9)
            java.lang.String r4 = securename6
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            long r4 = r1.getSize()
            boolean r3 = isFileValid(r3, r4)
            if (r3 != 0) goto L_0x0e33
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = securename6
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r8)
            r5.append(r9)
            java.lang.String r11 = securename6
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            UnzipFile(r7, r3, r4)
        L_0x0e33:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.util.zip.ZipEntry r1 = r7.getEntry(r3)
            if (r1 == 0) goto L_0x0e93
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r3.append(r9)
            java.lang.String r4 = securename7
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            long r4 = r1.getSize()
            boolean r3 = isFileValid(r3, r4)
            if (r3 != 0) goto L_0x0e93
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = securename7
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.io.File r3 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            r4.append(r9)
            java.lang.String r5 = securename7
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            UnzipFile(r7, r2, r3)
        L_0x0e93:
            r11 = r29
            java.util.zip.ZipEntry r1 = r7.getEntry(r11)
            if (r1 == 0) goto L_0x0eb0
            long r2 = r1.getSize()
            r12 = r30
            boolean r2 = isFileValid(r12, r2)
            if (r2 != 0) goto L_0x0eb2
            java.io.File r2 = new java.io.File
            r2.<init>(r12)
            UnzipFile(r7, r11, r2)
            goto L_0x0eb2
        L_0x0eb0:
            r12 = r30
        L_0x0eb2:
            if (r27 == 0) goto L_0x0f60
            r27.release()     // Catch:{ IOException -> 0x0ef0 }
            if (r26 == 0) goto L_0x0f60
            r26.close()     // Catch:{ IOException -> 0x0ecd }
            r25.close()     // Catch:{ IOException -> 0x0ec3 }
            goto L_0x0f60
        L_0x0ec3:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x0eca:
            r0 = move-exception
            r2 = r0
            goto L_0x0ee1
        L_0x0ecd:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x0eca }
            r25.close()     // Catch:{ IOException -> 0x0eda }
        L_0x0ed7:
            r3 = -1
            return r3
        L_0x0eda:
            r0 = move-exception
            r3 = -1
            r4 = r0
            r4.printStackTrace()
            return r3
        L_0x0ee1:
            r25.close()     // Catch:{ IOException -> 0x0ee6 }
            throw r2
        L_0x0ee6:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x0eed:
            r0 = move-exception
            r2 = r0
            goto L_0x0f2d
        L_0x0ef0:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x0eed }
            if (r26 == 0) goto L_0x0f2b
            r26.close()     // Catch:{ IOException -> 0x0f0b }
            r25.close()     // Catch:{ IOException -> 0x0f01 }
            r4 = -1
            goto L_0x0f2c
        L_0x0f01:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x0f08:
            r0 = move-exception
            r3 = r0
            goto L_0x0f1f
        L_0x0f0b:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()     // Catch:{ all -> 0x0f08 }
            r25.close()     // Catch:{ IOException -> 0x0f18 }
            r4 = -1
            return r4
        L_0x0f18:
            r0 = move-exception
            r4 = -1
            r5 = r0
            r5.printStackTrace()
            return r4
        L_0x0f1f:
            r25.close()     // Catch:{ IOException -> 0x0f24 }
            throw r3
        L_0x0f24:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x0f2b:
            r4 = -1
        L_0x0f2c:
            return r4
        L_0x0f2d:
            if (r26 == 0) goto L_0x0f5f
            r26.close()     // Catch:{ IOException -> 0x0f41 }
            r25.close()     // Catch:{ IOException -> 0x0f37 }
            goto L_0x0f5f
        L_0x0f37:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x0f3e:
            r0 = move-exception
            r2 = r0
            goto L_0x0f53
        L_0x0f41:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x0f3e }
            r25.close()     // Catch:{ IOException -> 0x0f4c }
            goto L_0x0ed7
        L_0x0f4c:
            r0 = move-exception
            r3 = -1
            r4 = r0
            r4.printStackTrace()
            return r3
        L_0x0f53:
            r25.close()     // Catch:{ IOException -> 0x0f58 }
            throw r2
        L_0x0f58:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x0f5f:
            throw r2
        L_0x0f60:
            r1 = 0
            return r1
        L_0x0f63:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r6 = r28
            r12 = r10
            r10 = r19
            r1 = r0
            goto L_0x1316
        L_0x0f72:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r11 = r12
            r2 = r13
            r22 = r20
            r6 = r28
            r12 = r10
            r10 = r19
            r1 = r0
            r5 = r25
            r3 = r26
            r4 = r27
            goto L_0x1100
        L_0x0f87:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r12 = r10
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r1 = r0
            goto L_0x1316
        L_0x0f97:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r12 = r10
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r1 = r0
            r5 = r25
            r3 = r26
            r4 = r27
            goto L_0x1100
        L_0x0fad:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r12 = r30
            r1 = r0
            goto L_0x1316
        L_0x0fbe:
            r0 = move-exception
            r7 = r1
            r8 = r6
            r2 = r13
            r10 = r19
            r22 = r20
            r6 = r28
            r11 = r29
            r12 = r30
            r1 = r0
            r5 = r25
            r3 = r26
            r4 = r27
            goto L_0x1100
        L_0x0fd5:
            r0 = move-exception
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r12 = r10
            r10 = r19
            r1 = r0
            goto L_0x1316
        L_0x0fe9:
            r0 = move-exception
            r7 = r1
            r26 = r3
            r27 = r4
            r25 = r5
            r8 = r6
            r11 = r12
            r2 = r13
            r6 = r14
            r22 = r20
            r12 = r10
            r10 = r19
            r1 = r0
            goto L_0x1100
        L_0x0ffd:
            r0 = move-exception
            r18 = r2
            r26 = r3
            r27 = r4
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            goto L_0x1316
        L_0x1014:
            r0 = move-exception
            r18 = r2
            r26 = r3
            r27 = r4
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            goto L_0x1100
        L_0x102b:
            r0 = move-exception
            r26 = r3
            r27 = r4
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
            goto L_0x1316
        L_0x1042:
            r0 = move-exception
            r26 = r3
            r27 = r4
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
            goto L_0x1100
        L_0x1059:
            r0 = move-exception
            r26 = r3
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r27 = r4
            r18 = r16
            goto L_0x1316
        L_0x1070:
            r0 = move-exception
            r26 = r3
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
            goto L_0x1100
        L_0x1085:
            r0 = move-exception
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r26 = r3
            r27 = r4
            r18 = r16
            goto L_0x1316
        L_0x109c:
            r0 = move-exception
            r25 = r5
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
            goto L_0x1100
        L_0x10af:
            r0 = move-exception
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r26 = r3
            r27 = r4
            r25 = r5
            r18 = r16
            goto L_0x1316
        L_0x10c6:
            r0 = move-exception
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
            goto L_0x1100
        L_0x10d6:
            r0 = move-exception
            r16 = r2
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r26 = r3
            r27 = r4
            r25 = r5
            r18 = r16
            goto L_0x1316
        L_0x10ef:
            r0 = move-exception
            r16 = r2
            r17 = r7
            r22 = r10
            r10 = r11
            r11 = r12
            r2 = r13
            r7 = r1
            r12 = r8
            r8 = r6
            r6 = r14
            r1 = r0
            r18 = r16
        L_0x1100:
            r1.printStackTrace()     // Catch:{ all -> 0x130b }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r2)
            r13.append(r6)
            java.lang.String r13 = r13.toString()
            java.util.zip.ZipEntry r13 = r7.getEntry(r13)
            if (r13 == 0) goto L_0x116b
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r8)
            r14.append(r9)
            java.lang.String r15 = libname
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r16 = r3
            r15 = r4
            long r3 = r13.getSize()
            boolean r3 = isFileValid(r14, r3)
            if (r3 != 0) goto L_0x1168
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r8)
            r14.append(r9)
            r19 = r1
            java.lang.String r1 = libname
            r14.append(r1)
            java.lang.String r1 = r14.toString()
            r4.<init>(r1)
            UnzipFile(r7, r3, r4)
            goto L_0x1170
        L_0x1168:
            r19 = r1
            goto L_0x1170
        L_0x116b:
            r19 = r1
            r16 = r3
            r15 = r4
        L_0x1170:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            java.util.zip.ZipEntry r1 = r7.getEntry(r1)
            if (r1 == 0) goto L_0x11d0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r3.append(r9)
            java.lang.String r4 = securename6
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            long r13 = r1.getSize()
            boolean r3 = isFileValid(r3, r13)
            if (r3 != 0) goto L_0x11d0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = securename6
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r8)
            r13.append(r9)
            java.lang.String r14 = securename6
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r4.<init>(r13)
            UnzipFile(r7, r3, r4)
        L_0x11d0:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.util.zip.ZipEntry r1 = r7.getEntry(r3)
            if (r1 == 0) goto L_0x1230
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r3.append(r9)
            java.lang.String r4 = securename7
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            long r13 = r1.getSize()
            boolean r3 = isFileValid(r3, r13)
            if (r3 != 0) goto L_0x1230
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = securename7
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.io.File r3 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            r4.append(r9)
            java.lang.String r9 = securename7
            r4.append(r9)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            UnzipFile(r7, r2, r3)
        L_0x1230:
            java.util.zip.ZipEntry r1 = r7.getEntry(r11)
            if (r1 == 0) goto L_0x1248
            long r2 = r1.getSize()
            boolean r2 = isFileValid(r12, r2)
            if (r2 != 0) goto L_0x1248
            java.io.File r2 = new java.io.File
            r2.<init>(r12)
            UnzipFile(r7, r11, r2)
        L_0x1248:
            if (r15 == 0) goto L_0x1309
            r15.release()     // Catch:{ IOException -> 0x128b }
            if (r16 == 0) goto L_0x1309
            r16.close()     // Catch:{ IOException -> 0x1264 }
            if (r5 == 0) goto L_0x125f
            r5.close()     // Catch:{ IOException -> 0x1258 }
            goto L_0x125f
        L_0x1258:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x125f:
            goto L_0x1309
        L_0x1261:
            r0 = move-exception
            r2 = r0
            goto L_0x127a
        L_0x1264:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x1261 }
            if (r5 == 0) goto L_0x1278
            r5.close()     // Catch:{ IOException -> 0x1271 }
            r4 = -1
            goto L_0x1279
        L_0x1271:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x1278:
            r4 = -1
        L_0x1279:
            return r4
        L_0x127a:
            if (r5 == 0) goto L_0x1287
            r5.close()     // Catch:{ IOException -> 0x1280 }
            goto L_0x1287
        L_0x1280:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x1287:
            throw r2
        L_0x1288:
            r0 = move-exception
            r2 = r0
            goto L_0x12cf
        L_0x128b:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x1288 }
            if (r16 == 0) goto L_0x12cd
            r16.close()     // Catch:{ IOException -> 0x12a9 }
            if (r5 == 0) goto L_0x12a4
            r5.close()     // Catch:{ IOException -> 0x129d }
            r4 = -1
            goto L_0x12ce
        L_0x129d:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x12a4:
            r4 = -1
            goto L_0x12ce
        L_0x12a6:
            r0 = move-exception
            r3 = r0
            goto L_0x12bf
        L_0x12a9:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()     // Catch:{ all -> 0x12a6 }
            if (r5 == 0) goto L_0x12bd
            r5.close()     // Catch:{ IOException -> 0x12b6 }
            r9 = -1
            goto L_0x12be
        L_0x12b6:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
            r9 = -1
            return r9
        L_0x12bd:
            r9 = -1
        L_0x12be:
            return r9
        L_0x12bf:
            if (r5 == 0) goto L_0x12cc
            r5.close()     // Catch:{ IOException -> 0x12c5 }
            goto L_0x12cc
        L_0x12c5:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x12cc:
            throw r3
        L_0x12cd:
            r4 = -1
        L_0x12ce:
            return r4
        L_0x12cf:
            if (r16 == 0) goto L_0x1308
            r16.close()     // Catch:{ IOException -> 0x12e4 }
            if (r5 == 0) goto L_0x1308
            r5.close()     // Catch:{ IOException -> 0x12da }
            goto L_0x1308
        L_0x12da:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x12e1:
            r0 = move-exception
            r2 = r0
            goto L_0x12fa
        L_0x12e4:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ all -> 0x12e1 }
            if (r5 == 0) goto L_0x12f8
            r5.close()     // Catch:{ IOException -> 0x12f1 }
            r4 = -1
            goto L_0x12f9
        L_0x12f1:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x12f8:
            r4 = -1
        L_0x12f9:
            return r4
        L_0x12fa:
            if (r5 == 0) goto L_0x1307
            r5.close()     // Catch:{ IOException -> 0x1300 }
            goto L_0x1307
        L_0x1300:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r3 = -1
            return r3
        L_0x1307:
            throw r2
        L_0x1308:
            throw r2
        L_0x1309:
            r1 = -1
            return r1
        L_0x130b:
            r0 = move-exception
            r16 = r3
            r15 = r4
            r1 = r0
            r25 = r5
            r27 = r15
            r26 = r16
        L_0x1316:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.util.zip.ZipEntry r3 = r7.getEntry(r3)
            if (r3 == 0) goto L_0x1374
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            r4.append(r9)
            java.lang.String r5 = libname
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            long r13 = r3.getSize()
            boolean r4 = isFileValid(r4, r13)
            if (r4 != 0) goto L_0x1374
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r8)
            r13.append(r9)
            java.lang.String r14 = libname
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r5.<init>(r13)
            UnzipFile(r7, r4, r5)
        L_0x1374:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.util.zip.ZipEntry r3 = r7.getEntry(r4)
            if (r3 == 0) goto L_0x13d4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            r4.append(r9)
            java.lang.String r5 = securename6
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            long r13 = r3.getSize()
            boolean r4 = isFileValid(r4, r13)
            if (r4 != 0) goto L_0x13d4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            java.lang.String r5 = securename6
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r8)
            r13.append(r9)
            java.lang.String r14 = securename6
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r5.<init>(r13)
            UnzipFile(r7, r4, r5)
        L_0x13d4:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.util.zip.ZipEntry r3 = r7.getEntry(r4)
            if (r3 == 0) goto L_0x1434
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            r4.append(r9)
            java.lang.String r5 = securename7
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            long r13 = r3.getSize()
            boolean r4 = isFileValid(r4, r13)
            if (r4 != 0) goto L_0x1434
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            java.lang.String r2 = securename7
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r8)
            r5.append(r9)
            java.lang.String r9 = securename7
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            UnzipFile(r7, r2, r4)
        L_0x1434:
            java.util.zip.ZipEntry r2 = r7.getEntry(r11)
            if (r2 == 0) goto L_0x144c
            long r3 = r2.getSize()
            boolean r3 = isFileValid(r12, r3)
            if (r3 != 0) goto L_0x144c
            java.io.File r3 = new java.io.File
            r3.<init>(r12)
            UnzipFile(r7, r11, r3)
        L_0x144c:
            if (r27 == 0) goto L_0x150d
            r27.release()     // Catch:{ IOException -> 0x148f }
            if (r26 == 0) goto L_0x150d
            r26.close()     // Catch:{ IOException -> 0x1468 }
            if (r25 == 0) goto L_0x1463
            r25.close()     // Catch:{ IOException -> 0x145c }
            goto L_0x1463
        L_0x145c:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r3 = -1
            return r3
        L_0x1463:
            goto L_0x150d
        L_0x1465:
            r0 = move-exception
            r1 = r0
            goto L_0x147e
        L_0x1468:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ all -> 0x1465 }
            if (r25 == 0) goto L_0x147c
            r25.close()     // Catch:{ IOException -> 0x1475 }
            r4 = -1
            goto L_0x147d
        L_0x1475:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x147c:
            r4 = -1
        L_0x147d:
            return r4
        L_0x147e:
            if (r25 == 0) goto L_0x148b
            r25.close()     // Catch:{ IOException -> 0x1484 }
            goto L_0x148b
        L_0x1484:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r3 = -1
            return r3
        L_0x148b:
            throw r1
        L_0x148c:
            r0 = move-exception
            r1 = r0
            goto L_0x14d3
        L_0x148f:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ all -> 0x148c }
            if (r26 == 0) goto L_0x14d1
            r26.close()     // Catch:{ IOException -> 0x14ad }
            if (r25 == 0) goto L_0x14a8
            r25.close()     // Catch:{ IOException -> 0x14a1 }
            r4 = -1
            goto L_0x14d2
        L_0x14a1:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x14a8:
            r4 = -1
            goto L_0x14d2
        L_0x14aa:
            r0 = move-exception
            r3 = r0
            goto L_0x14c3
        L_0x14ad:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()     // Catch:{ all -> 0x14aa }
            if (r25 == 0) goto L_0x14c1
            r25.close()     // Catch:{ IOException -> 0x14ba }
            r5 = -1
            goto L_0x14c2
        L_0x14ba:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
            r5 = -1
            return r5
        L_0x14c1:
            r5 = -1
        L_0x14c2:
            return r5
        L_0x14c3:
            if (r25 == 0) goto L_0x14d0
            r25.close()     // Catch:{ IOException -> 0x14c9 }
            goto L_0x14d0
        L_0x14c9:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x14d0:
            throw r3
        L_0x14d1:
            r4 = -1
        L_0x14d2:
            return r4
        L_0x14d3:
            if (r26 == 0) goto L_0x150c
            r26.close()     // Catch:{ IOException -> 0x14e8 }
            if (r25 == 0) goto L_0x150c
            r25.close()     // Catch:{ IOException -> 0x14de }
            goto L_0x150c
        L_0x14de:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r3 = -1
            return r3
        L_0x14e5:
            r0 = move-exception
            r1 = r0
            goto L_0x14fe
        L_0x14e8:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ all -> 0x14e5 }
            if (r25 == 0) goto L_0x14fc
            r25.close()     // Catch:{ IOException -> 0x14f5 }
            r4 = -1
            goto L_0x14fd
        L_0x14f5:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
            r4 = -1
            return r4
        L_0x14fc:
            r4 = -1
        L_0x14fd:
            return r4
        L_0x14fe:
            if (r25 == 0) goto L_0x150b
            r25.close()     // Catch:{ IOException -> 0x1504 }
            goto L_0x150b
        L_0x1504:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r3 = -1
            return r3
        L_0x150b:
            throw r1
        L_0x150c:
            throw r1
        L_0x150d:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wrapper.proxyapplication.Util.PrepareSecurefiles(android.content.Context, java.util.zip.ZipFile):int");
    }

    private static boolean isFileValid(String path, long length) {
        File tmpfile = new File(path);
        if (!tmpfile.exists() || tmpfile.length() != length) {
            return false;
        }
        return true;
    }

    public static long getCRC32(File fileUri) {
        try {
            CheckedInputStream checkedinputstream = new CheckedInputStream(new BufferedInputStream(new FileInputStream(fileUri)), new CRC32());
            while (checkedinputstream.read(new byte[65536]) >= 0) {
            }
            long crc = checkedinputstream.getChecksum().getValue();
            checkedinputstream.close();
            return crc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String CreatenewFileName(String Oldfilename, String SplitString, String InsertString) {
        int index = Oldfilename.lastIndexOf(SplitString);
        if (index < 0) {
            return null;
        }
        return Oldfilename.substring(ERROR_FALSE, index) + InsertString + Oldfilename.substring(index, Oldfilename.length());
    }

    public static boolean SafeUnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir) {
        return SafeUnzipFile(zf, filepathinzip, fileinfiledir, 0);
    }

    public static boolean SafeUnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir, long crc) {
        BufferedOutputStream Output_fos = null;
        BufferedInputStream bufbr = null;
        try {
            ZipEntry ze = zf.getEntry(filepathinzip);
            if (ze == null) {
                if (Output_fos != null) {
                    try {
                        Output_fos.close();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return false;
                            }
                        }
                        return false;
                    } catch (Throwable e4) {
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                return false;
                            }
                        }
                        throw e4;
                    }
                }
                return false;
            }
            if (crc != 0) {
                if (ze.getCrc() == crc) {
                    if (Output_fos != null) {
                        try {
                            Output_fos.close();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                    return false;
                                }
                            }
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        } catch (Throwable e9) {
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e10) {
                                    e10.printStackTrace();
                                    return false;
                                }
                            }
                            throw e9;
                        }
                    }
                    return true;
                }
            }
            byte[] buf = UnzipFile(zf, ze);
            if (ERROR_SUCCESS != 0) {
                Output_fos = new BufferedOutputStream(new FileOutputStream(fileinfiledir));
                Output_fos.write(buf, ERROR_FALSE, buf.length);
            }
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e12) {
                    e12.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e13) {
                            e13.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable e14) {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e15) {
                            e15.printStackTrace();
                            return false;
                        }
                    }
                    throw e14;
                }
            }
            return true;
        } catch (Exception e16) {
            e16.printStackTrace();
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e17) {
                            e17.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e18) {
                    e18.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e19) {
                            e19.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable e20) {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e21) {
                            e21.printStackTrace();
                            return false;
                        }
                    }
                    throw e20;
                }
            }
            return false;
        } catch (Throwable th) {
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e23) {
                    e23.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e24) {
                            e24.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable e25) {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e26) {
                            e26.printStackTrace();
                            return false;
                        }
                    }
                    throw e25;
                }
            }
            throw th;
        }
    }

    public static byte[] UnzipFile(ZipFile zf, ZipEntry ze) throws IOException {
        byte[] buf = new byte[((int) ze.getSize())];
        BufferedInputStream bufbr = new BufferedInputStream(zf.getInputStream(ze));
        int totallen = ERROR_FALSE;
        do {
            int readlen = bufbr.read(buf, totallen, ((int) ze.getSize()) - totallen);
            if (readlen < 0) {
                break;
            }
            totallen += readlen;
        } while (((long) totallen) != ze.getSize());
        if (totallen == ((int) ze.getSize())) {
            return buf;
        }
        throw new IOException("incorrect zip file size");
    }

    private static long getFileCRC32(File file) {
        long result = -1;
        byte[] filebuf = new byte[((int) file.length())];
        BufferedInputStream filebr = null;
        CRC32 crc32 = new CRC32();
        try {
            filebr = new BufferedInputStream(new FileInputStream(file));
            int totallen = ERROR_FALSE;
            while (true) {
                int readlen = filebr.read(filebuf);
                if (readlen < 0) {
                    break;
                }
                crc32.update(filebuf);
                totallen += readlen;
            }
            result = crc32.getValue();
            try {
                filebr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            if (filebr != null) {
                filebr.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (filebr != null) {
                filebr.close();
            }
        } catch (Throwable th) {
            if (filebr != null) {
                try {
                    filebr.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return result;
    }

    private static boolean checkCopiedFileCrc(ZipFile zf, String filepathinzip, File file) {
        long crc = getFileCRC32(file);
        if (crc == -1) {
            return false;
        }
        try {
            ZipEntry ze = zf.getEntry(filepathinzip);
            if (ze == null || crc == 0 || ze.getCrc() != crc) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
