package com.wrapper.proxyapplication;

import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

public class MultiDexForTinker {
    static final String TAG = "MultiDexForTinker";
    static IOException[] dexElementsSuppressedExceptions = null;
    static int hasInjected = 0;
    static int injectDexBeginIndex = 0;
    static Object[] injectDexsObj = null;
    static Object[] injectFilesObj = null;
    static Object[] injectPathListObj = null;
    static Object[] injectPathsObj = null;
    static Object[] injectZipsObj = null;

    private MultiDexForTinker() {
    }

    private static ArrayList<File> splitPaths(String searchPath) {
        ArrayList<File> result = new ArrayList<>();
        if (searchPath != null) {
            for (String path : searchPath.split(File.pathSeparator)) {
                result.add(new File(path));
            }
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static String getprefixname(String fullname) {
        String filename;
        int preindex = fullname.lastIndexOf("/");
        if (preindex >= 0) {
            filename = fullname.substring(preindex + 1);
        } else {
            filename = fullname;
        }
        int sufindex = filename.lastIndexOf(".");
        if (sufindex >= 0) {
            return filename.substring(0, sufindex);
        }
        return filename;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x0067=Splitter:B:29:0x0067, B:24:0x005d=Splitter:B:24:0x005d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.Object> openallDexes(java.lang.ClassLoader r15, java.lang.String r16, java.lang.String r17) {
        /*
            r10 = 0
            java.util.ArrayList r5 = splitPaths(r16)
            java.io.File r1 = new java.io.File
            r0 = r17
            r1.<init>(r0)
            r7 = 0
            r8 = 0
            java.lang.String r11 = "pathList"
            java.lang.reflect.Field r10 = findField(r15, r11)     // Catch:{ NoSuchFieldException -> 0x0061 }
            java.lang.Object r2 = r10.get(r15)     // Catch:{ IllegalAccessException -> 0x005c, IllegalArgumentException -> 0x0066 }
            java.lang.String r11 = "loadDexFile"
            r12 = 2
            java.lang.Class[] r12 = new java.lang.Class[r12]     // Catch:{ NoSuchMethodException -> 0x0057 }
            r13 = 0
            java.lang.Class<java.io.File> r14 = java.io.File.class
            r12[r13] = r14     // Catch:{ NoSuchMethodException -> 0x0057 }
            r13 = 1
            java.lang.Class<java.io.File> r14 = java.io.File.class
            r12[r13] = r14     // Catch:{ NoSuchMethodException -> 0x0057 }
            java.lang.reflect.Method r6 = findMethod(r2, r11, r12)     // Catch:{ NoSuchMethodException -> 0x0057 }
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ InvocationTargetException -> 0x0077 }
            r9.<init>()     // Catch:{ InvocationTargetException -> 0x0077 }
            java.util.Iterator r3 = r5.iterator()     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
        L_0x0034:
            boolean r11 = r3.hasNext()     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            if (r11 != 0) goto L_0x003c
            r8 = r9
        L_0x003b:
            return r8
        L_0x003c:
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            r12 = 0
            java.lang.Object r13 = r3.next()     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            r11[r12] = r13     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            r12 = 1
            r11[r12] = r1     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            java.lang.Object r7 = r6.invoke(r2, r11)     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            r9.add(r7)     // Catch:{ InvocationTargetException -> 0x0051, NoSuchMethodException -> 0x0074, IllegalAccessException -> 0x0071, IllegalArgumentException -> 0x006e, NoSuchFieldException -> 0x006b }
            goto L_0x0034
        L_0x0051:
            r4 = move-exception
            r8 = r9
        L_0x0053:
            r4.printStackTrace()     // Catch:{ NoSuchMethodException -> 0x0057 }
            goto L_0x003b
        L_0x0057:
            r4 = move-exception
        L_0x0058:
            r4.printStackTrace()     // Catch:{ IllegalAccessException -> 0x005c, IllegalArgumentException -> 0x0066 }
            goto L_0x003b
        L_0x005c:
            r4 = move-exception
        L_0x005d:
            r4.printStackTrace()     // Catch:{ NoSuchFieldException -> 0x0061 }
            goto L_0x003b
        L_0x0061:
            r4 = move-exception
        L_0x0062:
            r4.printStackTrace()
            goto L_0x003b
        L_0x0066:
            r4 = move-exception
        L_0x0067:
            r4.printStackTrace()     // Catch:{ NoSuchFieldException -> 0x0061 }
            goto L_0x003b
        L_0x006b:
            r4 = move-exception
            r8 = r9
            goto L_0x0062
        L_0x006e:
            r4 = move-exception
            r8 = r9
            goto L_0x0067
        L_0x0071:
            r4 = move-exception
            r8 = r9
            goto L_0x005d
        L_0x0074:
            r4 = move-exception
            r8 = r9
            goto L_0x0058
        L_0x0077:
            r4 = move-exception
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wrapper.proxyapplication.MultiDexForTinker.openallDexes(java.lang.ClassLoader, java.lang.String, java.lang.String):java.util.ArrayList");
    }

    private static ArrayList<Object> installDexes(ClassLoader loader, String jarordexpathlist, String Odexpath) throws IOException {
        ArrayList<File> jarordexfilelist = splitPaths(jarordexpathlist);
        File Odexdir = new File(Odexpath);
        try {
            return V19.install(loader, findField(loader, "pathList"), jarordexfilelist, Odexdir);
        } catch (IllegalArgumentException e5) {
            e5.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IOException e) {
            throw new IOException("v19,load dex fail");
        } catch (NoSuchFieldException e4) {
            try {
                return V4.install(loader, findField(loader, "path"), jarordexfilelist, Odexdir);
            } catch (IllegalArgumentException e12) {
                e12.printStackTrace();
            } catch (IllegalAccessException e22) {
                e22.printStackTrace();
            } catch (IOException e6) {
                throw new IOException("v4, load dex fail");
            } catch (NoSuchFieldException e7) {
            }
        }
        return null;
    }

    private static ArrayList<Object> installDexes(ClassLoader loader, String jarordexpathlist, String Odexpath, int index) throws IOException {
        ArrayList<File> jarordexfilelist = splitPaths(jarordexpathlist);
        File Odexdir = new File(Odexpath);
        try {
            return V19.install(loader, findField(loader, "pathList"), jarordexfilelist, Odexdir, index);
        } catch (IllegalArgumentException e5) {
            e5.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IOException e) {
            throw new IOException("v19,load dex fail");
        } catch (NoSuchFieldException e4) {
            try {
                return V4.install(loader, findField(loader, "path"), jarordexfilelist, Odexdir, index);
            } catch (IllegalArgumentException e12) {
                e12.printStackTrace();
            } catch (IllegalAccessException e22) {
                e22.printStackTrace();
            } catch (IOException e6) {
                throw new IOException("v4, load dex fail");
            } catch (NoSuchFieldException e7) {
            }
        }
        return null;
    }

    private static void preparetoinstallDexes(ClassLoader loader, int dexnum) throws IOException {
        try {
            try {
                prepareexpandFieldArray(findField(loader, "pathList").get(loader), "dexElements", dexnum);
            } catch (IllegalArgumentException e5) {
                e5.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            try {
                Field pathListField = findField(loader, "path");
                try {
                    prepareexpandFieldArray(loader, "mPaths", dexnum);
                    prepareexpandFieldArray(loader, "mFiles", dexnum);
                    prepareexpandFieldArray(loader, "mZips", dexnum);
                    prepareexpandFieldArray(loader, "mDexs", dexnum);
                } catch (IllegalArgumentException e12) {
                    e12.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            } catch (NoSuchFieldException e3) {
            }
        }
    }

    private static void finishinstallDexes(ClassLoader loader) {
        try {
            try {
                Object dexPathList = findField(loader, "pathList").get(loader);
                findField(dexPathList, "dexElements").set(dexPathList, injectPathListObj);
                if (dexElementsSuppressedExceptions != null) {
                    findField(dexPathList, "dexElementsSuppressedExceptions").set(dexPathList, dexElementsSuppressedExceptions);
                }
            } catch (IllegalArgumentException e5) {
                e5.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            try {
                try {
                    Object dexPathList2 = findField(loader, "path").get(loader);
                    findField(dexPathList2, "mPaths").set(dexPathList2, injectPathsObj);
                    findField(dexPathList2, "mFiles").set(dexPathList2, injectFilesObj);
                    findField(dexPathList2, "mZips").set(dexPathList2, injectZipsObj);
                    findField(dexPathList2, "mDexs").set(dexPathList2, injectDexsObj);
                } catch (IllegalArgumentException e12) {
                    e12.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            } catch (NoSuchFieldException e3) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    private static Method findMethodinClazz(Class<?> cls, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<? super Object> clazz;
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                Class<? super Object> superclass = clazz.getSuperclass();
                clazz = cls;
                clazz = superclass;
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + clazz);
    }

    /* access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Method method = cls.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements, int index) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field findField = findField(instance, fieldName);
        if (fieldName.equals("dexElements")) {
            System.arraycopy(extraElements, 0, injectPathListObj, injectDexBeginIndex + index, extraElements.length);
        } else if (fieldName.equals("mPaths")) {
            System.arraycopy(extraElements, 0, injectPathsObj, injectDexBeginIndex + index, extraElements.length);
        } else if (fieldName.equals("mFiles")) {
            System.arraycopy(extraElements, 0, injectFilesObj, injectDexBeginIndex + index, extraElements.length);
        } else if (fieldName.equals("mDexs")) {
            System.arraycopy(extraElements, 0, injectDexsObj, injectDexBeginIndex + index, extraElements.length);
        } else if (fieldName.equals("mZips")) {
            System.arraycopy(extraElements, 0, injectZipsObj, injectDexBeginIndex + index, extraElements.length);
        }
    }

    private static void prepareexpandFieldArray(Object instance, String fieldName, int dexnum) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object[] original = (Object[]) findField(instance, fieldName).get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + dexnum);
        System.arraycopy(original, 0, combined, 0, original.length);
        injectDexBeginIndex = original.length;
        if (fieldName.equals("dexElements")) {
            injectPathListObj = combined;
        } else if (fieldName.equals("mPaths")) {
            injectPathsObj = combined;
        } else if (fieldName.equals("mFiles")) {
            injectFilesObj = combined;
        } else if (fieldName.equals("mZips")) {
            injectZipsObj = combined;
        } else if (fieldName.equals("mDexs")) {
            injectDexsObj = combined;
        }
    }

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Object[] DexElementslist = makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions);
            if (DexElementslist.length != additionalClassPathEntries.size()) {
                throw new IOException("load dex failed");
            }
            ArrayList<Object> objcookieordexfilelist = new ArrayList<>();
            for (Object DexElements : DexElementslist) {
                Object objdexfile = MultiDexForTinker.findField(DexElements, "dexFile").get(DexElements);
                Field cookieField = MultiDexForTinker.findField(objdexfile, "mCookie");
                if (cookieField.getType().getName().equals("int")) {
                    objcookieordexfilelist.add(Integer.valueOf(cookieField.getInt(objdexfile)));
                } else if (cookieField.getType().getName().equals("long")) {
                    objcookieordexfilelist.add(Long.valueOf(cookieField.getLong(objdexfile)));
                } else {
                    objcookieordexfilelist.add(objdexfile);
                }
            }
            MultiDexForTinker.expandFieldArray(dexPathList, "dexElements", DexElementslist);
            if (suppressedExceptions.size() > 0) {
                Iterator<IOException> it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    IOException next = it.next();
                }
                Field suppressedExceptionsField = MultiDexForTinker.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions2.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
            }
            MultiDexForTinker.hasInjected = 1;
            return objcookieordexfilelist;
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory, int index) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Object[] DexElementslist = makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions);
            if (DexElementslist.length != additionalClassPathEntries.size()) {
                throw new IOException("load dex failed");
            }
            ArrayList<Object> objcookieordexfilelist = new ArrayList<>();
            for (Object DexElements : DexElementslist) {
                Object objdexfile = MultiDexForTinker.findField(DexElements, "dexFile").get(DexElements);
                Field cookieField = MultiDexForTinker.findField(objdexfile, "mCookie");
                if (cookieField.getType().getName().equals("int")) {
                    objcookieordexfilelist.add(Integer.valueOf(cookieField.getInt(objdexfile)));
                } else if (cookieField.getType().getName().equals("long")) {
                    objcookieordexfilelist.add(Long.valueOf(cookieField.getLong(objdexfile)));
                } else {
                    objcookieordexfilelist.add(objdexfile);
                }
            }
            MultiDexForTinker.expandFieldArray(dexPathList, "dexElements", DexElementslist, index);
            if (suppressedExceptions.size() > 0) {
                Iterator<IOException> it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    IOException next = it.next();
                }
                MultiDexForTinker.dexElementsSuppressedExceptions = (IOException[]) MultiDexForTinker.findField(dexPathList, "dexElementsSuppressedExceptions").get(dexPathList);
                if (MultiDexForTinker.dexElementsSuppressedExceptions == null) {
                    MultiDexForTinker.dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + MultiDexForTinker.dexElementsSuppressedExceptions.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(MultiDexForTinker.dexElementsSuppressedExceptions, 0, combined, suppressedExceptions.size(), MultiDexForTinker.dexElementsSuppressedExceptions.length);
                    MultiDexForTinker.dexElementsSuppressedExceptions = combined;
                }
            }
            MultiDexForTinker.hasInjected = 1;
            return objcookieordexfilelist;
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) {
            try {
                try {
                    return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                } catch (RuntimeException e4) {
                    e4.printStackTrace();
                }
            } catch (NoSuchMethodException e5) {
                try {
                    try {
                        return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e12) {
                        e12.printStackTrace();
                    } catch (InvocationTargetException e13) {
                        e13.printStackTrace();
                    } catch (RuntimeException e14) {
                        e14.printStackTrace();
                    }
                } catch (NoSuchMethodException e15) {
                    try {
                    } catch (NoSuchMethodException e22) {
                        e22.printStackTrace();
                    }
                    try {
                        return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makePathElements", List.class, File.class, List.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
                    } catch (IllegalAccessException e23) {
                        e23.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (IllegalArgumentException e24) {
                        e24.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (InvocationTargetException e25) {
                        e25.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    }
                }
            }
        }
    }

    private static final class V4 {
        private V4() {
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            StringBuilder path = new StringBuilder((String) pathListField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            if (extraDexs.length != extraSize) {
                throw new IOException("load dex failed");
            }
            ArrayList<Object> objcookielist = new ArrayList<>();
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                String odexdirPath = optimizedDirectory.getAbsolutePath();
                String odexprefix = MultiDexForTinker.getprefixname(entryPath);
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, String.valueOf(odexdirPath) + "/" + odexprefix + ".dex", 0);
                objcookielist.add(Integer.valueOf(MultiDexForTinker.findField(extraDexs[index], "mCookie").getInt(extraDexs[index])));
            }
            pathListField.set(loader, path.toString());
            MultiDexForTinker.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDexForTinker.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDexForTinker.expandFieldArray(loader, "mZips", extraZips);
            MultiDexForTinker.expandFieldArray(loader, "mDexs", extraDexs);
            MultiDexForTinker.hasInjected = 1;
            return objcookielist;
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory, int index) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            StringBuilder path = new StringBuilder((String) pathListField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            if (extraDexs.length != extraSize) {
                throw new IOException("load dex failed");
            }
            ArrayList<Object> objcookielist = new ArrayList<>();
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                String odexdirPath = optimizedDirectory.getAbsolutePath();
                String odexprefix = MultiDexForTinker.getprefixname(entryPath);
                path.append(':').append(entryPath);
                int iteIndex = iterator.previousIndex();
                extraPaths[iteIndex] = entryPath;
                extraFiles[iteIndex] = additionalEntry;
                extraZips[iteIndex] = new ZipFile(additionalEntry);
                extraDexs[iteIndex] = DexFile.loadDex(entryPath, String.valueOf(odexdirPath) + "/" + odexprefix + ".dex", 0);
                objcookielist.add(Integer.valueOf(MultiDexForTinker.findField(extraDexs[iteIndex], "mCookie").getInt(extraDexs[iteIndex])));
            }
            pathListField.set(loader, path.toString());
            MultiDexForTinker.expandFieldArray(loader, "mPaths", extraPaths, index);
            MultiDexForTinker.expandFieldArray(loader, "mFiles", extraFiles, index);
            MultiDexForTinker.expandFieldArray(loader, "mZips", extraZips, index);
            MultiDexForTinker.expandFieldArray(loader, "mDexs", extraDexs, index);
            MultiDexForTinker.hasInjected = 1;
            if (objcookielist.size() == 0) {
                return null;
            }
            return objcookielist;
        }
    }
}
