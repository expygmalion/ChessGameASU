
Hello guys.. These are the git commands that you can use



#### 1. Always Start by Pulling
Pull the latest changes from the remote repository to avoid conflicts:
```bash
git pull origin main
```
(Replace `main` with the branch you're working on.)

---

#### 2. Adding Files
When you make changes, add the files to the staging area:
```bash
git add .
```
(The `.` adds all modified files. You can also specify a file, e.g., `git add file.txt`.)

---

#### 3. Committing Changes
Save your changes with a meaningful message:
```bash
git commit -m "Your commit message"
```

---

#### 4. Pushing Changes
Push your committed changes to the remote repository:
```bash
git push origin branch_name
```
(Replace `branch_name` with the branch you're working on.)

---

#### 5. Creating a New Branch
To create a new branch and switch to it:
```bash
git checkout -b branch_name
```

---

#### 6. Switching Branches
Switch to an existing branch:
```bash
git checkout branch_name
```

---

#### 7. Merging Branches
To merge another branch into the branch you're on:
```bash
git merge branch_name
```
(Pull before merging: `git pull origin main`.)

---

#### 8. Deleting a Branch
- Locally:
  ```bash
  git branch -d branch_name
  ```
  (Force delete: `git branch -D branch_name`.)
- Remotely:
  ```bash
  git push origin --delete branch_name
  ```

---

### Important Notes
1. **Always Pull Before Making Changes:**  
   Run `git pull origin branch_name` to stay updated with the latest changes.
2. **Keep Commit Messages Clear:**  
   Describe what you changed briefly but clearly.
3. **Push Your Changes After Committing:**  
   Never leave unpushed commits on your local machine.


