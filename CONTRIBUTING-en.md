To be a collaborator
---

When more than one person use a language which you can code (whether there are more than one person using a language can be judged according to the assessment of the issue).

* Each person creates a package for himself to distinguish different people(if sub module has exists). Refer to `java-leetcode`. 
For the first time, please submit by PR, you can obtain the permission of collaborator after this PR is merged.
* Everyone creates a new one in their own package named `packageName.md`, add the index and then link it to the current sub module README.md, refer to `java-leetcode`.

If there is no language in this repository. Please submit PR to create a new sub module like follow example. 
Direct push is not allowed for create a new module. And you should support `GitHub action workflows` as much as possible. 

this is a python sub module, if you want to create a new module that the language you use is not exists you should look at this structure.

```
python-leetcode
    /laozhang   
       /leetcode_12.py  
       /laozhang.md
    /dreamylost 
       /leetcode_12.py
       /dreamylost.md
    README.md
```

edit `python-leetcode/README.md`, let's make `packageName.md` linked to `python-leetcode/README.md`, such as

```
[laozhang](./laozhang/laozhang.md)
[dreamylost](./dreamylost/dreamylost.md)
```

## requirement

* When non script language is used, it must be compiled locally. The JVM platform uses `gradle spotlessApply` to format the code, which is not required by script language
* No code passed GitHub action workflow, please roll back immediately! (at present, this repository only supports checking JVM language and Rust)
* If it doesn't pass sonarcloud, please roll back immediately!
* If it fails to pass lgtm, please take time to solve it
* Conflict resolution: only append is allowed, not delete
* The copyright of the default java code is: `all collaborator`