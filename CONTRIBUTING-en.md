To be a collaborator
---

Please submit a PR to create a new sub module like the following example when there is no language in this repository. 
Direct push is not allowed for creating a new module. You should support `GitHub action workflows` as much as possible. 

* Each person creates a package for himself to distinguish different people(if sub module has exists). 
For the first time, please submit by PR, you can obtain the permission of collaborator after this PR is merged.
* Everyone creates a new one in their own package named `packageName.md` in sub module, add the index and then link it to the current sub module README.md, refer to `python-leetcode`.

> After obtaining the permission of collaborator, please use `git push` cautiously. Before each push, first pull the latest remote code and resolve the conflict locally (be sure to keep others' commit well in case of conflict).

This is a python sub module, if you want to create a new module that the language you use is not exists you should look at this structure.

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
* The copyright of the default is : `Apache-2.0 All collaborators`

> If the data structure definition already exists, you can try to use the existing one.