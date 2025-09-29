document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('year').textContent = new Date().getFullYear();

    const contactForm = document.getElementById('contact-form');
    const formStatus = document.getElementById('form-status');
    const sendButton = contactForm.querySelector('button[type="submit"]');

    let isSubmitting = false; // <<< 1. NEW FLAG

    contactForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (isSubmitting) { // <<< 2. CHECK FLAG
            return; // Ignore the second click
        }

        isSubmitting = true; // Set flag to true
        sendButton.disabled = true; // Disable button visually

        formStatus.textContent = 'Sending...';
        formStatus.style.color = 'var(--text-light)';

        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            message: document.getElementById('message').value,
        };

        try {
            const response = await fetch('/api/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                formStatus.textContent = 'Message sent successfully! Thank you.';
                formStatus.style.color = '#415a77';
                contactForm.reset();
            } else {
                throw new Error('Server responded with an error.');
            }
        } catch (error) {
            console.error('Form submission error:', error);
            formStatus.textContent = 'An error occurred. Please try again.';
            formStatus.style.color = '#ff4f4f';

            // Re-enable button and reset flag only on failure
            sendButton.disabled = false;
        } finally {
            // 3. Reset flag AFTER the entire process finishes (except on success, where we keep button disabled)
            if (!response?.ok) {
                isSubmitting = false;
            }
        }
    });
});